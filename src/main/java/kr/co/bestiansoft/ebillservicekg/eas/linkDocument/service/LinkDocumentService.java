package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service;

import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;

public interface LinkDocumentService {
    int insertLinkDocument(LinkDocumentVo vo);
    void deleteLinkDocument(String fromDocId, String toDocId);
}
