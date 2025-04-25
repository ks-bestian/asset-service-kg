package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;

import java.util.List;

public interface WorkRequestService {
    int insertWorkRequest(WorkRequestVo vo);
    int deleteWorkRequest (String workReqId);
    int updateWorkStatus (String workReqId, String workStatus);
    List<WorkRequestAndResponseVo> getWorkRequestList(String docId);
}
