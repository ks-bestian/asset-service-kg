package kr.co.bestiansoft.ebillservicekg.eas.file.repository;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EasFileRepository {
    int insertEasFile (EasFileVo vo);
    List<EasFileVo> getAttachFiles(String docId , String fileType);
    EasFileVo getRecentFileByDocIdAndFileType(String docId, String fileType);
    void updatePdfFileInfo(EasFileVo vo);
    EasFileVo getFileById(String fileId);
    void deleteDocument(String docId);
}
