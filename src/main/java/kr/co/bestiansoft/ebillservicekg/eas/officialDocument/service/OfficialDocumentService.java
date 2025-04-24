package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;

import java.util.List;

public interface OfficialDocumentService {

    List<DocumentListDto> getDocumentList(SearchDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    int saveAllDocument(InsertDocumentVo vo);
    void updateStatusOfficialDocument(String billId, String status);
    int countDocumentList();
    DocumentDetailDto getDocumentDetail(int rcvId);
}
