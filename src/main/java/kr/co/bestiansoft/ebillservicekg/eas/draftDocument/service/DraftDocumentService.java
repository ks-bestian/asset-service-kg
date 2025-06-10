package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service;

import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;

import java.util.Map;

public interface DraftDocumentService {

    DraftDocumentVo insertDraftDocument(int formId,  Map<String, String> map);
    void updateDraftStatus(int aarsDocId, String aarsStatusCd);
    DraftDocumentVo getDraftDocument(String aarsDocId);
}
