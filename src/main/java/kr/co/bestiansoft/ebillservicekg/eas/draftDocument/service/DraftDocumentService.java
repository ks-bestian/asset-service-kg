package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service;

import java.util.Map;

import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;

public interface DraftDocumentService {

    DraftDocumentVo insertDraftDocument(int formId,  Map<String, String> map);
    void updateDraftStatus(int aarsDocId, String aarsStatusCd);
    DraftDocumentVo getDraftDocument(int aarsDocId);
}
