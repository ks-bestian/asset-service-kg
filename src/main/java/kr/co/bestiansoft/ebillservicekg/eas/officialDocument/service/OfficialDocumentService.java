package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;

import java.util.List;

public interface OfficialDocumentService {

    List<DocumentListDto> getDocumentList(SearchDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    int updateStatusOfficialDocument(String docId, String status);
    int countDocumentList();
    DocumentDetailDto getDocumentDetail(String docId);
    List<DocumentUserDto> getDocumentUser(String docId);
    Boolean isReject(String docId);
    List<DocumentListDto> getRejectDocumentList(SearchDocumentVo vo);
    int countRejectDocument();
    List<DocumentListDto> getEndDocumentList(SearchDocumentVo vo);
    List<DocumentListDto> getMyDocumentList(SearchDocumentVo vo);

}
