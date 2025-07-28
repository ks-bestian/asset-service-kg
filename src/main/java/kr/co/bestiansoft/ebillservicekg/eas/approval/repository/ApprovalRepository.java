package kr.co.bestiansoft.ebillservicekg.eas.approval.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;

@Mapper
public interface ApprovalRepository {
    int insertApproval(ApprovalVo vo);
    void updateStatus(String apvlId, String apvlStatusCd);
    void updateApproval(UpdateApprovalVo vo);
    List<ApprovalVo> getApprovals(String docId);
    ApprovalVo getApproval(int apvlId);
    ApprovalVo getApprovalsByUserId(String userId, String docId);
    void deleteDocument(String docId);
}
