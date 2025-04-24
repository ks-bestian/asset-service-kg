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

    @Override
    public int insertWorkRequest(WorkRequestVo vo) {
        return workRequestRepository.insertWorkRequest(vo);
    }

    @Override
    public void deleteWorkRequest(String workReqId) {
        workRequestRepository.deleteWorkRequest(workReqId);
    }

    @Override
    public void updateWorkStatus(String workReqId, String workStatus) {
        workRequestRepository.updateWorkStatus(workReqId, workStatus);
    }
}
