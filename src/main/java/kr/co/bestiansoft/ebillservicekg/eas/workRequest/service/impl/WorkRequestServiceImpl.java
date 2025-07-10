package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
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
    public int deleteWorkRequest(int workReqId) {
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
                    List<WorkResponseVo> responses = workResponseService.getWorkResponses(request.getWorkReqId());
                    return new WorkRequestAndResponseVo().from(request, responses);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of work requests and their corresponding responses based on the specified document ID.
     *
     * @param docId the unique identifier of the document whose work requests and responses are to be retrieved
     * @return a list of WorkRequestAndResponseVo objects representing the work requests and their related responses
     */
    @Override
    public List<WorkRequestAndResponseVo> getWorkRequestList(String docId) {
        return workRequestRepository.getWorkRequestList(null, docId)
                .stream()
                .map(request -> {
                    List<WorkResponseVo> responses = workResponseService.getWorkResponses(request.getWorkReqId());
                    return new WorkRequestAndResponseVo().from(request, responses);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a combined work request and response object based on the specified document ID.
     *
     * @param docId the unique identifier of the document whose associated work request and response are to be retrieved
     * @return a WorkRequestAndResponseVo object containing the combined work request and response data;
     *         returns null if no matching work request is found
     */
    @Override
    public WorkRequestAndResponseVo getWorkRequestAndResponseList(String docId) {
        WorkRequestAndResponseVo workRequestAndResponseVo = new WorkRequestAndResponseVo();

        WorkRequestVo workRequestVo = workRequestRepository.getWorkRequestListByUserId(docId, new SecurityInfoUtil().getAccountId());

        if(workRequestVo != null){
            List<WorkResponseVo> responses = workResponseService.getWorkResponses(workRequestVo.getWorkReqId());
            return workRequestAndResponseVo.from(workRequestVo,responses);
        }else{
            log.info("workRequestVo is null");
            return workRequestAndResponseVo;
        }
    }

    /**
     * Deletes a document identified by the provided document ID.
     *
     * @param docId the unique identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        workRequestRepository.deleteDocument(docId);
    }

    /**
     * Retrieves the document ID associated with the specified work request ID.
     *
     * @param workReqId the unique identifier of the work request
     * @return the document ID linked to the provided work request ID, or null if no match is found
     */
    @Override
    public String getDocIdByWorkReqId(int workReqId) {
        return workRequestRepository.getDocIdByWorkReqId(workReqId);
    }

    /**
     * Updates the details of an existing work request in the repository.
     *
     * @param vo the WorkRequestVo object containing the updated details of the work request
     * @return the number of records updated, typically 1 if successful
     */
    @Override
    public int updateWorkRequest(WorkRequestVo vo) {return workRequestRepository.updateWorkRequest(vo);}
}
