package kr.co.bestiansoft.ebillservicekg.eas.approval.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.repository.ApprovalRepository;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    @Override
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
    @Override
	public List<ApprovalVo> getApprovals(String docId){
        return  approvalRepository.getApprovals(docId);
    }


    @Override
	public ApprovalVo getApproval(int apvlId){
        return approvalRepository.getApproval(apvlId);
    }

    @Override
	public ApprovalVo getApprovalsByUserId(String docId){
        return approvalRepository.getApprovalsByUserId(new SecurityInfoUtil().getAccountId(), docId);
    }

    /**
     * Deletes the document associated with the specified document ID from the repository.
     *
     * @param docId the unique identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        approvalRepository.deleteDocument(docId);
    }

}
