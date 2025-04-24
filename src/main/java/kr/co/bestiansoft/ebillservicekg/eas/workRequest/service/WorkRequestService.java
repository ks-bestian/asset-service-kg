package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;

public interface WorkRequestService {
    int insertWorkRequest(WorkRequestVo vo);
    void deleteWorkRequest (String workReqId);
    void updateWorkStatus (String workReqId, String workStatus);
}
