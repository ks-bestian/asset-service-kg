package kr.co.bestiansoft.ebillservicekg.eas.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;

@Mapper
public interface EasFileRepository {
    int insertEasFile (EasFileVo vo);
    List<EasFileVo> getAttachFiles(String docId , String fileType);
    EasFileVo getRecentFileByDocIdAndFileType(String docId, String fileType);
    void updatePdfFileInfo(EasFileVo vo);
    EasFileVo getFileById(String fileId);
    void deleteDocument(String docId);
}
