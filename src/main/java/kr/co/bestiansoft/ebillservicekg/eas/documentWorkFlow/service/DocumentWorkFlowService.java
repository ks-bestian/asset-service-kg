package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;

public interface DocumentWorkFlowService {
    int saveAllDocument(InsertDocumentVo vo);
    void approve(UpdateApprovalVo vo);
    int updateReadDateTime(int rcvId);
    void updateReadApproval(int apvlId);
    void approveReject(UpdateApprovalVo vo);
    void reception(WorkRequestAndResponseVo vo);
    void rejectReception(UpdateReceivedInfoVo vo);
    void endDocument(String docId);
    void saveReplyDocument(InsertDocumentVo vo);
    void rewriteDocument(InsertDocumentVo vo);
    void endRejectedDocument(String docId);
    void updateWorkRequest(WorkRequestAndResponseVo vo);
    void updateMainResponser(UpdateReceivedInfoVo vo);
    void insertWorkRequest(WorkRequestAndResponseVo vo);
    void registerWorkResponse(UpdateWorkResponseVo vo);
    void deleteWorkRequest(int workReqId);
}
