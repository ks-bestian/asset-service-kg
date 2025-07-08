package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl;


import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository.WorkResponseRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.WorkResponseService;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkResponseServiceImpl implements WorkResponseService {
    private final WorkResponseRepository workResponseRepository;

    /**
     * Inserts a work response record into the repository.
     *
     * @param vo the WorkResponseVo object containing the details of the work*/
    @Override
    public int insertWorkResponse(WorkResponseVo vo) {
        return workResponseRepository.insertWorkResponse(vo);
    }

    /**
     * Updates work response details using the provided data object.
     *
     * @param vo the UpdateWorkResponseVo object containing updated work response details
     * @return the number of records updated
     */
    @Override
    public int updateWorkResponse(UpdateWorkResponseVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        vo.setRspnsDtm(LocalDateTime.now());
        return workResponseRepository.updateWorkContents(vo);
    }

    /**
     * Deletes a work request by its ID.
     *
     * @param workReqId the identifier of the work request to be deleted
     * @return the number of records deleted
     */
    @Override
    public void deleteWorkRequestId(int workReqId) {
        workResponseRepository.deleteWorkRequestId(workReqId);
    }

    /**
     * Retrieves a list of work response records based on the provided receiver ID.
     *
     * @param rcvId the ID of the receiver for which work responses are to be retrieved
     * @return a list of WorkResponseVo objects representing the work responses
     */
    @Override
    public List<WorkResponseVo> getWorkResponse(int rcvId) {
        return workResponseRepository.getWorkResponseByRcvId(rcvId, null);
    }

    /**
     * Retrieves a list of work response records based on the provided document ID.
     *
     * @param docId the ID of the document for which work responses are to be retrieved
     * @return a list of WorkResponseVo objects representing the work responses
     */
    public List<WorkResponseVo> getWorkResponse(String docId) {
        return workResponseRepository.getWorkResponseByRcvId(null, docId);
    }

    /**
     * Retrieves a list of work response records based on the provided work request ID
     * and the ID of the currently authenticated user.
     *
     * @param workReqId the identifier of the work request for which work responses are to be retrieved
     * @return a list of WorkResponseVo objects representing the work responses associated with the given work request ID
     *         and the current user
     */
    public List<WorkResponseVo> getWorkResponseByUserId(int workReqId) {
        return workResponseRepository.getWorkResponseByUserId(workReqId, new SecurityInfoUtil().getAccountId());
    }

    /**
     * Updates the read timestamp for a specific work response record identified by its ID.
     *
     * @param rspnsId the identifier of the work response record to be updated
     */
    public void updateReadDtm(int rspnsId) {
        UpdateWorkResponseVo vo = UpdateWorkResponseVo.builder()
                .rspnsId(rspnsId)
                .checkDtm(LocalDateTime.now())
                .build();

        workResponseRepository.updateWorkContents(vo);
    }

    /**
     * Deletes a document identified by the provided document ID.
     *
     * @param docId the identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        workResponseRepository.deleteDocument(docId);
    }

    /**
     * Retrieves a list of work response records based on the provided work request ID.
     *
     * @param workReqId the identifier of the work request for which work responses are to be retrieved
     * @return a list of WorkResponseVo objects representing the work responses associated with the given work request ID
     */
    @Override
    public List<WorkResponseVo> getWorkResponses(int workReqId) {
        return workResponseRepository.getWorkResponses(workReqId);
    }

    /**
     * Retrieves a list of user identifiers who have responded to the specified work request.
     *
     * @param workReqId the identifier of the work request for which responded user identifiers are to be retrieved
     * @return a list of strings representing the user identifiers of those who have responded to the given work request
     */
    @Override
    public List<String> getRespondedUsers(int workReqId) {
        return workResponseRepository.getRespondedUsers(workReqId);
    }

}
