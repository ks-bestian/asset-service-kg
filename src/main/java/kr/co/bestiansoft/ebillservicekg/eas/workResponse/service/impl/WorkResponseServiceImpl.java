package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl;


import kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository.WorkResponseRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.WorkResponseService;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * Updates the work response details in the repository.
     *
     * @param vo the UpdateWorkResponseVo object containing updated work response details
     */
    @Override
    public void updateWorkResponse(UpdateWorkResponseVo vo) {
        workResponseRepository.updateWorkContents(vo);
    }

    /**
     * Deletes a work request record based on the provided work request ID.
     *
     * @param workReqId the unique identifier of the work request to be deleted
     */
    @Override
    public void deleteWorkRequestId(String workReqId) {
        workResponseRepository.deleteWorkRequestId(workReqId);
    }
}
