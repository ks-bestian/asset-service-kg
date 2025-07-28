package kr.co.bestiansoft.ebillservicekg.eas.file.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.SaveFileDto;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.UpdatePdfFileDto;

public interface EasFileService {
    List<String> uploadEasFileAndConversionPdf (EasFileVo vo);
    int saveEasFile(EasFileVo vo);
    List<EasFileVo> getAttachFiles(String docId, String fileType);
    EasFileVo getFileById(String fileId);
    EasFileVo getFileByDocIdAndFileType(String docId, String fileType);


//    commonness save file(edv) , pdf file
    SaveFileDto saveFile(MultipartFile file);
    String saveFile(File file);
    CompletableFuture<UpdatePdfFileDto> savePdfFile(byte[] fileBytes, String fileName);
    CompletableFuture<UpdatePdfFileDto> savePdfFile(File file);
    void deleteDocument(String docId);
}
