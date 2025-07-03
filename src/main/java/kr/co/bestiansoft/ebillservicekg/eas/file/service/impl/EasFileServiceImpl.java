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
     * Upload and properly handle the EAS (electronic payment system) file.
     * This method starts a file validation, storing file information and PDF conversion if necessary.
     *
     * @param vo an instance of {@code EasFileVo} which contains file metadata, list of MultipartFile objects,
     *           and information necessary for saving file details.
     */
    @Override
    public List<String> uploadEasFile(EasFileVo vo){
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

            if (!isPdfFile(fileVo.getFileExt())) {
                String fileId = fileVo.getFileId();
                try {
                    UpdatePdfFileDto pdfDto = savePdfFile(file);
                    updatePdfInfo(fileId, pdfDto);
                    log.info("PDF Conversion completed: Original file ID {}, PDF file ID {}", fileId, pdfDto.getPdfFileId());
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
     * Save the Easfilevo object in the repository.
     * This method delegates the storage task to the inserteasfile method of the repository.
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
     * Update the PDF information of the specified file. This method uses the provided file ID
     *After searching the file in the repository, updating the PDF file ID and name with the information of the DTO,
     *Save the changes in the repository.
     *
     * @param fileId the ID of the file whose PDF information is to be updated
     * @param dto an UpdatePdfFileDto containing the new PDF file ID and name 
     *            to be associated with the file
     */
    @Transactional
    public void updatePdfInfo(String fileId, UpdatePdfFileDto dto){
        EasFileVo fileVo = easFileRepository.getFileById(fileId);
        if (fileVo != null) {
            // PDF information update
            fileVo.setPdfFileId(dto.getPdfFileId());
            fileVo.setPdfFileNm(dto.getPdfFileNm());

            easFileRepository.updatePdfFileInfo(fileVo);
        }

    }
    /**
     * Retrieves a list of attached files associated with the specified document ID.
     * Queries the repository to fetch file metadata linked to the document.
     *
     * Search the list of attachments associated with the specified document ID.
     *Query in the repository and get the file metadata connected to the document.
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
     * Save the provided files and create metadata associated with the stored file.
     *The file is stored using a unique file ID, and the original file name for metadata,
     *File size and file extension are included.
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
     * Convert the input file into a PDF format, save and save.
     *Process temporary file creation and guarantee appropriate theorem after processing.
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
            //Create temporary file
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

    @Override
    public void deleteDocument(String docId) {
        easFileRepository.deleteDocument(docId);
    }

    /**
     * Determines if a given file extension represents a PDF file.
     *
     * Make sure the given file extension is a PDF file.
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
     * Copy the selected properties from the provided Easfilevo object and 
     *Create a new instance.
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
