package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;

import java.util.List;

public interface OfficialDocumentService {

    List<OfficialDocumentVo> getOfficialDocument (OfficialDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
}
