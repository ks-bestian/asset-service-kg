package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;

public interface DocumentWorkFlowService {
    int saveAllDocument(InsertDocumentVo vo);
    void approve(UpdateApprovalVo vo);
    int updateReadDateTime(String docId);
}
