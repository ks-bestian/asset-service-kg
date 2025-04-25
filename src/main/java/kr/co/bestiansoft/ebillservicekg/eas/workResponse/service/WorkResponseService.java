package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;

import java.util.List;

public interface WorkResponseService {
    int insertWorkResponse(WorkResponseVo vo);
    int updateWorkResponse(UpdateWorkResponseVo vo);
    int deleteWorkRequestId(String workReqId);
    List<WorkResponseVo> getWorkResponse(int workReqId);
}
