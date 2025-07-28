package kr.co.bestiansoft.ebillservicekg.eas.approval.service;


import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;

public interface ApprovalService {
    int insertApproval(ApprovalVo vo);
    void updateStatus(String apvlId, String apvlStatusCd);
    void updateApproval(UpdateApprovalVo vo);
    List<ApprovalVo> getApprovals (String docId);
    ApprovalVo getApproval(int apvlId);
    ApprovalVo getApprovalsByUserId(String docId);
    void deleteDocument(String docId);
}
