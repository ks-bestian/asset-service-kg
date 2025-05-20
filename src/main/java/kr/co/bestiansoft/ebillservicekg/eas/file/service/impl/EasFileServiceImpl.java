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
import java.util.List;
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
    public void uploadEasFile(EasFileVo vo){
        String userId = new SecurityInfoUtil().getAccountId();
        vo.setRegId(userId);
        vo.setRegDt(LocalDateTime.now());
        vo.setDeleteYn('N');

        for(MultipartFile file : vo.getFiles()){
            EasFileVo fileVo = createFileVoFrom(vo);
            SaveFileDto savedFileDto = saveFile(file);

            fileVo = fileVo.fromSaveFileDto(savedFileDto, fileVo);

            saveEasFile(fileVo);

            if (!isPdfFile(fileVo.getFileExt())) {
                String fileId = fileVo.getFileId();
                try {
                    UpdatePdfFileDto pdfDto = savePdfFile(file);
                    updatePdfInfo(fileId, pdfDto);
                    log.info("PDF 변환 완료: 원본 파일 ID {}, PDF 파일 ID {}", fileId, pdfDto.getPdfFileId());
                } catch (Exception e) {
                    log.error("PDF 변환 실패: 파일 ID {}, 오류: {}", fileId, e.getMessage(), e);
                }
            }

        }
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
    @Transactional
    public void updatePdfInfo(String fileId, UpdatePdfFileDto dto){
        EasFileVo fileVo = easFileRepository.getFileById(fileId);
        if (fileVo != null) {
            // PDF 정보 업데이트
            fileVo.setPdfFileId(dto.getPdfFileId());
            fileVo.setPdfFileNm(dto.getPdfFileNm());

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
    public List<EasFileVo> getAttachFiles(String docId) {
        return easFileRepository.getAttachFiles(docId);
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
     * Saves a PDF file by converting the input file to a PDF format and storing it.
     * Handles temporary file creation and ensures proper cleanup post-processing.
     *
     * 입력 파일을 PDF 형식으로 변환하여 저장하고 저장합니다.
     * 임시 파일 생성을 처리하고 처리 후 적절한 정리를 보장합니다.
     *
     * @param file the multipart file to be converted to a PDF and saved
     * @return an UpdatePdfFileDto containing the PDF file's ID and name
     * @throws RuntimeException if the file conversion or saving process fails
     */
    public UpdatePdfFileDto savePdfFile(MultipartFile file) {
        UpdatePdfFileDto result = new UpdatePdfFileDto();
        String fileId = StringUtil.getUUUID();
        String fileName = file.getOriginalFilename();
        File tmpFile = null;
        File tmpPdfFile = null;

        try{
            // 임시 파일 생성
            tmpFile =File.createTempFile("temp", null);
            tmpPdfFile = File.createTempFile("pdfTemp", null);
            file.transferTo(tmpFile);
            boolean pdfResult = pdfService.convertToPdf(tmpFile.getAbsolutePath(), fileName, tmpPdfFile.getAbsolutePath());
            if(pdfResult){
                edv.save(fileId, new FileInputStream(tmpPdfFile));
            }
        }catch (Exception e){
            throw new RuntimeException("FILE_NOT_SAVE", e);
        }finally {
            tmpFile.delete();
            tmpPdfFile.delete();
        }

        result.setPdfFileId(fileId);
        result.setPdfFileNm(fileName);
        return result;
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
