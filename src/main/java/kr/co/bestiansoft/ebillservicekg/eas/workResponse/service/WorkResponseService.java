package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;

public interface WorkResponseService {
    int insertWorkResponse(WorkResponseVo vo);
    void updateWorkResponse(UpdateWorkResponseVo vo);
    void deleteWorkRequestId(String workReqId);
}
