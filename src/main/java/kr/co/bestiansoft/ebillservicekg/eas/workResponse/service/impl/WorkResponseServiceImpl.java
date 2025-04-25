package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl;


import kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository.WorkResponseRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.WorkResponseService;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return workResponseRepository.updateWorkContents(vo);
    }

    /**
     * Deletes a work request by its ID.
     *
     * @param workReqId the identifier of the work request to be deleted
     * @return the number of records deleted
     */
    @Override
    public int deleteWorkRequestId(String workReqId) {
        return workResponseRepository.deleteWorkRequestId(workReqId);
    }

    @Override
    public List<WorkResponseVo> getWorkResponse(int workReqId) {
        return workResponseRepository.getWorkResponse(workReqId);
    }
}
