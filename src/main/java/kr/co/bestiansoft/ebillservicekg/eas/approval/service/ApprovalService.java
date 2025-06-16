package kr.co.bestiansoft.ebillservicekg.eas.approval.service;


import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;

import java.util.List;

public interface ApprovalService {
    int insertApproval(ApprovalVo vo);
    void updateStatus(String apvlId, String apvlStatusCd);
    void updateApproval(UpdateApprovalVo vo);
    List<ApprovalVo> getApprovals (String docId);
    List<ApprovalLIstDto> getApprovalList(SearchDocumentVo vo);
    int countApprovalList();
    ApprovalVo getApproval(int apvlId);
    ApprovalVo getApprovalsByUserId(String docId);
}
