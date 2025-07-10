package kr.co.bestiansoft.ebillservicekg.eas.file.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.eas.file.repository.EasFileRepository;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.SaveFileDto;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.UpdatePdfFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@RequiredArgsConstructor
@Slf4j
@Service
public class EasFileServiceImpl implements EasFileService {

    private final EasFileRepository easFileRepository;
    private final EDVHelper edv;
    private final PdfService pdfService;
    private final ExecutorService executorService;

    /**
     * Uploads an EAS (Electronic Approval System) file and processes it accordingly.
     * This method handles file validation, saves file information, and initiates PDF conversion if needed.
     *
     * EAS(전자결재시스템) 파일을 업로드하고 적절히 처리합니다.
     * 이 메서드는 파일 유효성 검사, 파일 정보 저장 및 필요한 경우 PDF 변환을 시작합니다.
     *
     * @param vo an instance of {@code EasFileVo} which contains file metadata, list of MultipartFile objects,
     *           and information necessary for saving file details.
     */
    @Override
    public List<String> uploadEasFileAndConversionPdf(EasFileVo vo){
        log.info(vo.toString());
        String userId = new SecurityInfoUtil().getAccountId();
        vo.setRegId(userId);
        vo.setRegDt(LocalDateTime.now());
        vo.setDeleteYn('N');
        List<String> ids = new ArrayList<>();
        for(MultipartFile file : vo.getFiles()){
            EasFileVo fileVo = createFileVoFrom(vo);
            SaveFileDto savedFileDto = saveFile(file);

            fileVo = fileVo.fromSaveFileDto(savedFileDto, fileVo);

            saveEasFile(fileVo);
            log.info("EasFileVo: {}", fileVo.toString());
            if (!isPdfFile(fileVo.getFileExt())) {
                String fileId = fileVo.getFileId();
                log.info("PDF Conversion not required: Original file ID {}", fileId);
                try {
                    byte[] fileBytes = file.getBytes();
                    String originalFilename = file.getOriginalFilename();

                    CompletableFuture<UpdatePdfFileDto> futureResult = savePdfFile(fileBytes, originalFilename);
                    futureResult.thenAccept(pdfDto -> {
                        updatePdfInfo(fileId, pdfDto);
                        log.info("PDF Conversion completed: Original file ID {}, PDF file ID {}", fileId, pdfDto.getPdfFileId());
                    }).exceptionally(err ->{
                        log.error("PDF conversion failure: File ID {}, Error: {}", fileId, err.getMessage(), err);
                        return null;
                    });


                } catch (Exception e) {
                    log.error("PDF conversion failure: File ID {}, Error: {}", fileId, e.getMessage(), e);
                }
            }

            ids.add(fileVo.getFileId());
        }
        return ids;
    }

    /**
     * Saves an EasFileVo object into the repository.
     * This method delegates the save operation to the repository's insertEasFile method.
     *
     * EasFileVo 객체를 저장소에 저장합니다.
     * 이 메서드는 저장 작업을 리포지토리의 insertEasFile 메서드에 위임합니다.
     *
     * @param vo the EasFileVo object containing the file's metadata and other associated details
     * @return an integer representing the result of the database insert operation; typically, 
     *         the number of rows affected
     */
    public int saveEasFile(EasFileVo vo){
        return easFileRepository.insertEasFile(vo);
    }

    /**
     * Updates the PDF information for a specified file. This method retrieves 
     * the file from the repository using the provided file ID, updates its 
     * PDF file ID and name with the information from the DTO, and saves the 
     * changes back to the repository.
     *
     * 지정된 파일의 PDF 정보를 업데이트합니다. 이 메서드는 제공된 파일 ID를 사용하여
     * 저장소에서 파일을 검색하고, DTO의 정보로 PDF 파일 ID와 이름을 업데이트한 후,
     * 변경 사항을 저장소에 다시 저장합니다.
     *
     * @param fileId the ID of the file whose PDF information is to be updated
     * @param dto an UpdatePdfFileDto containing the new PDF file ID and name 
     *            to be associated with the file
     */
    public void updatePdfInfo(String fileId, UpdatePdfFileDto dto){
        EasFileVo fileVo = easFileRepository.getFileById(fileId);
        log.info("updatePdfInfo: fileId: {}, dto: {}", fileId, dto);

        if (fileVo != null) {
            // PDF information update
            fileVo.setPdfFileId(dto.getPdfFileId());
            fileVo.setPdfFileNm(dto.getPdfFileNm());
            log.info(fileVo.toString());
            easFileRepository.updatePdfFileInfo(fileVo);
        }

    }
    /**
     * Retrieves a list of attached files associated with the specified document ID.
     * Queries the repository to fetch file metadata linked to the document.
     *
     * 지정된 문서 ID와 관련된 첨부 파일 목록을 검색합니다.
     * 저장소에 쿼리하여 문서에 연결된 파일 메타데이터를 가져옵니다.
     *
     * @param docId the ID of the document for which to retrieve attached files
     * @return a list of EasFileVo objects representing the attached files; 
     *         an empty list if no files are attached to the specified document
     */
    @Override
    public List<EasFileVo> getAttachFiles(String docId, String fileType) {
        return easFileRepository.getAttachFiles(docId , fileType);
    }

    @Override
    public EasFileVo getFileById(String fileId) {
        return easFileRepository.getFileById(fileId);
    }

    /**
     * Saves the provided file and generates metadata related to the saved file.
     * The file is stored using a unique file ID, and its metadata includes 
     * the original filename, file size, and file extension.
     *
     * 제공된 파일을 저장하고 저장된 파일과 관련된 메타데이터를 생성합니다.
     * 파일은 고유한 파일 ID를 사용하여 저장되며, 메타데이터에는 원본 파일명,
     * 파일 크기 및 파일 확장자가 포함됩니다.
     *
     * @param file the MultipartFile to be saved
     * @return a SaveFileDto containing metadata such as the file ID, original
     *         filename, file size, and file extension
     * @throws RuntimeException if an error occurs while saving the file
     */
    public SaveFileDto saveFile(MultipartFile file){
        SaveFileDto result = new SaveFileDto();
        String fileId = StringUtil.getUUUID();

        try(InputStream edvIs = file.getInputStream()){
            edv.save(fileId, edvIs);
        }catch (Exception e){
            throw new RuntimeException("EDV_NOT_WORK", e);
        }

        String originalFilename  = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);


        result.setFileExt(fileExt);
        result.setFileId(fileId);
        result.setFileSize((int) file.getSize());
        result.setOrgFileNm(originalFilename);

        return result;
    }

    /**
     * Saves the given file and returns a unique identifier for the saved file.
     *
     * @param file the file to be saved
     * @return a unique identifier for the saved file
     * @throws RuntimeException if an error occurs during the file saving process
     */
    public String saveFile(File file){
        String fileId = StringUtil.getUUUID();
        try (InputStream edvIs = new FileInputStream(file)){
            edv.save(fileId, edvIs);
        } catch (Exception e){
            throw new RuntimeException("EDV_NOT_WORK", e);
        }
        return fileId;
    }

    /**
     * Saves the provided PDF file after converting it if needed. This method creates temporary files
     * for processing and uploads the finalized PDF content to a storage service.
     *
     * @param fileBytes the byte array representing the content of the file to be saved
     * @param fileName the name of the file to be associated with the stored PDF
     * @return a CompletableFuture containing an UpdatePdfFileDto object with details about the saved PDF file
     */
    public CompletableFuture<UpdatePdfFileDto> savePdfFile(byte[] fileBytes, String fileName) {

        return CompletableFuture.supplyAsync(() -> {

            String fileId = StringUtil.getUUUID();
            UpdatePdfFileDto result = new UpdatePdfFileDto();

            File tmpFile = null;
            File tmpPdfFile = null;

            try {
                //Create temporary file
                tmpFile = File.createTempFile("temp", ".tmp");
                tmpPdfFile = File.createTempFile("pdfTemp", ".pdf");

                java.nio.file.Files.write(tmpFile.toPath(), fileBytes);

                boolean pdfResult = pdfService.convertToPdf(tmpFile.getAbsolutePath(), fileName, tmpPdfFile.getAbsolutePath());

                if (pdfResult) {
                    edv.save(fileId, new FileInputStream(tmpPdfFile));
                }else {
                    throw new RuntimeException("PDF_CONVERSION_FAILED");
                }
                result.setPdfFileId(fileId);
                result.setPdfFileNm(fileName);

                return result;
            } catch (Exception e) {
                throw new RuntimeException("FILE_NOT_SAVE", e);
            } finally {
                tmpFile.delete();
                tmpPdfFile.delete();
            }


        }, executorService);
    }

    /**
     * Saves a PDF file by converting the input file to a PDF format and storing it.
     *
     * @param file The file to be converted to PDF and saved.
     * @return A CompletableFuture containing an UpdatePdfFileDto with details of the saved PDF file.
     * @throws RuntimeException If the PDF conversion fails or the file cannot be saved.
     */
    public CompletableFuture<UpdatePdfFileDto> savePdfFile(File file) {

        return CompletableFuture.supplyAsync(() -> {
            String fileId = StringUtil.getUUUID();
            String fileName = file.getName();
            UpdatePdfFileDto result = new UpdatePdfFileDto();


            File tmpPdfFile = null;

            try{
                tmpPdfFile = File.createTempFile("pdfTemp", ".pdf");
                boolean pdfResult = pdfService.convertToPdf(file.getAbsolutePath(), fileName, tmpPdfFile.getAbsolutePath());

                if (pdfResult) {
                    edv.save(fileId, new FileInputStream(tmpPdfFile));
                }else {
                    throw new RuntimeException("PDF_CONVERSION_FAILED");
                }
                result.setPdfFileId(fileId);
                result.setPdfFileNm(fileName);

                return result;
            }catch (Exception e) {
                throw new RuntimeException("FILE_NOT_SAVE", e);
            }finally {
                tmpPdfFile.delete();
            }
        }, executorService);
    }

    /**
     * Deletes a document based on the provided document identifier.
     *
     * @param docId the unique identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        easFileRepository.deleteDocument(docId);
    }

    /**
     * Determines if a given file extension represents a PDF file.
     *
     * 주어진 파일 확장자가 PDF 파일인지 확인합니다.
     *
     * @param fileExt the file extension to check; expected to be a string, 
     *                such as "pdf" or "PDF"
     * @return true if the file extension matches "pdf" (case-insensitive), 
     *         false otherwise
     */
    public boolean isPdfFile(String fileExt){
        return fileExt.equals("pdf") || fileExt.equals("PDF");
    }

    /**
     * Creates a new instance of EasFileVo by copying selected properties 
     * from the provided easFileVo object.
     *
     * 제공된 easFileVo 객체에서 선택된 속성을 복사하여 EasFileVo의 
     * 새 인스턴스를 생성합니다.
     *
     * @param easFileVo the source EasFileVo instance from which properties 
     *                  will be copied
     * @return a new EasFileVo instance with values derived from the input object
     */
    public EasFileVo createFileVoFrom(EasFileVo easFileVo){
        return  EasFileVo.builder()
                .docId(easFileVo.getDocId())
                .fileType(easFileVo.getFileType())
                .deleteYn(easFileVo.getDeleteYn())
                .regId(easFileVo.getRegId())
                .regDt(easFileVo.getRegDt())
                .build();
    }
}
