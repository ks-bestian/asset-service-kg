package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository.WorkRequestRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.WorkRequestService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.WorkResponseService;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkRequestServiceImpl implements WorkRequestService {

    private final WorkRequestRepository workRequestRepository;
    private final WorkResponseService workResponseService;

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
     * Deletes a work request identified by the provided ID.
     *
     * @param workReqId the unique identifier of the work request to be deleted
     * @return the number of records deleted, typically 1 if successful
     */
    @Override
    public int deleteWorkRequest(String workReqId) {
        return workRequestRepository.deleteWorkRequest(workReqId);
    }

    /**
     * Updates the status of the work request identified by the given ID.
     *
     * @param workReqId the unique identifier of the work request to update
     * @param workStatus the new status to be updated for the work request
     * @return the number of records affected by the update, typically 1 if successful
     */
    @Override
    public int updateWorkStatus(String workReqId, String workStatus) {
        return workRequestRepository.updateWorkStatus(workReqId, workStatus);
    }


    /**
     * Retrieves a list of work requests associated with the specified document ID,
     * and enriches each work request with its corresponding responses.
     *
     * @param docId the document ID used to filter work requests
     * @return a list of WorkRequestAndResponseVo objects, where each object contains
     *         details of the work request and its associated responses
     */
    @Override
    public List<WorkRequestAndResponseVo> getWorkRequestList(String docId) {
        return workRequestRepository.getWorkRequestList(docId)
                .stream()
                .map(request -> {
                    List<WorkResponseVo> responses = workResponseService.getWorkResponse(request.getWorkReqId());
                    return new WorkRequestAndResponseVo().from(request, responses);
                })
                .collect(Collectors.toList());
    }

}
