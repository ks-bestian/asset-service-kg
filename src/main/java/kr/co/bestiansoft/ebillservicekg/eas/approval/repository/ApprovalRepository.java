package kr.co.bestiansoft.ebillservicekg.eas.approval.repository;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
