package kr.co.bestiansoft.ebillservicekg.eas.file.service;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.SaveFileDto;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.UpdatePdfFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EasFileService {
    List<String> uploadEasFile (EasFileVo vo);
    int saveEasFile(EasFileVo vo);
    List<EasFileVo> getAttachFiles(String docId, String fileType);

//    공통 save file(edv) , pdf file
    SaveFileDto saveFile(MultipartFile file);
    UpdatePdfFileDto savePdfFile(MultipartFile file);
}
