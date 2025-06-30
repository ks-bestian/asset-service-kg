package kr.co.bestiansoft.ebillservicekg.eas.approval.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.repository.ApprovalRepository;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    /**
     * Inserts a new approval record into the repository.
     *
     * @param vo the ApprovalVo object containing approval information to be inserted
     * @return the number of rows affected by the insert operation
     */
    public int insertApproval(ApprovalVo vo){
        return approvalRepository.insertApproval(vo);
    }

    /**
     * Updates the status of an approval record in the repository.
     *
     * @param apvlId the unique identifier of the approval record to be updated
     * @param apvlStatusCd the new status code to be set for the approval record
     */
    @Override
    public void updateStatus(String apvlId, String apvlStatusCd){
        approvalRepository.updateStatus(apvlId, apvlStatusCd);
    }

    /**
     * Updates an existing approval record in the repository with the provided details.
     *
     * @param vo the UpdateApprovalVo object containing the updated approval information
     */
    @Override
    public void updateApproval(UpdateApprovalVo vo) {
        approvalRepository.updateApproval(vo);
    }

    /**
     * Retrieves a list of approval records associated with the given document ID.
     *
     * @param docId the unique identifier of the document for which approval records are to be fetched
     * @return a list of ApprovalVo objects containing the approval details
     */
    public List<ApprovalVo> getApprovals(String docId){
        return  approvalRepository.getApprovals(docId);
    }


    public ApprovalVo getApproval(int apvlId){
        return approvalRepository.getApproval(apvlId);
    }

    public ApprovalVo getApprovalsByUserId(String docId){
        return approvalRepository.getApprovalsByUserId(new SecurityInfoUtil().getAccountId(), docId);
    }

    @Override
    public void deleteDocument(String docId) {
        approvalRepository.deleteDocument(docId);
    }

}
