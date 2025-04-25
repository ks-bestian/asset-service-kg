package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository.WorkRequestRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.WorkRequestService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkRequestServiceImpl implements WorkRequestService {

    private final WorkRequestRepository workRequestRepository;

    /**
     * Inserts a work request record into the repository.
     *
     * @param vo the WorkRequestVo object containing the details of the work request to be inserted
     * @return the number of records inserted, typically 1 if successful
     */
    @Override
    public int insertWorkRequest(WorkRequestVo vo) {
        return workRequestRepository.insertWorkRequest(vo);
    }

    /**
     * Deletes a work request from the repository based on the provided work request ID.
     *
     * @param workReqId the unique identifier of the work request to be deleted
     */
    @Override
    public void deleteWorkRequest(String workReqId) {
        workRequestRepository.deleteWorkRequest(workReqId);
    }

    /**
     * Updates the status of a work request in the repository.
     *
     * @param workReqId the unique identifier of the work request
     * @param workStatus the new status to be assigned to the work request
     */
    @Override
    public void updateWorkStatus(String workReqId, String workStatus) {
        workRequestRepository.updateWorkStatus(workReqId, workStatus);
    }
}
