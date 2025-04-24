package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OfficialDocumentMapper {
    List<DocumentListDto> getDocumentList(SearchDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    void updateStatus(String billId, String docStatusCd);
    int countDocumentList(String userId);
    DocumentUserDto getDocumentUser(String docId);
    DocumentDetailDto getDocumentDetail(String userId, int rcvId);
}
