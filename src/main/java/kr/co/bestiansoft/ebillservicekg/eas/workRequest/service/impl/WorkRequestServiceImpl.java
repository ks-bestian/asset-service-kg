package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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
     * Retrieves a list of work requests based on the specified receiver ID.
     *
     * @param rcvId the unique identifier of the receiver whose work requests are to be fetched
     * @return a list of WorkRequestVo objects representing the work requests associated with the given receiver ID
     */
    @Override
    public List<WorkRequestAndResponseVo> getWorkRequestList(int rcvId) {
        return workRequestRepository.getWorkRequestList(rcvId, null).stream()
                .map(request -> {
                    List<WorkResponseVo> responses = workResponseService.getWorkResponse(request.getRcvId());
                    return new WorkRequestAndResponseVo().from(request, responses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkRequestAndResponseVo> getWorkRequestList(String docId) {
        return workRequestRepository.getWorkRequestList(null, docId).stream()
                .map(request -> {
                    List<WorkResponseVo> responses = workResponseService.getWorkResponse(docId);
                    return new WorkRequestAndResponseVo().from(request, responses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public WorkRequestAndResponseVo getWorkRequestAndResponseList(String docId) {
        WorkRequestAndResponseVo workRequestAndResponseVo = new WorkRequestAndResponseVo();

        WorkRequestVo workRequestVo = workRequestRepository.getWorkRequestListByUserId(docId, new SecurityInfoUtil().getAccountId());
        if(workRequestVo != null){
            workRequestAndResponseVo.from(workRequestVo,workResponseService.getWorkResponseByUserId(workRequestVo.getWorkReqId()));
            return workRequestAndResponseVo;
        }else{
            return null;
        }
    }

    @Override
    public void deleteDocument(String docId) {
        workRequestRepository.deleteDocument(docId);
    }
}
