package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;

import java.util.List;

public interface WorkRequestService {
    int insertWorkRequest(WorkRequestVo vo);
    int deleteWorkRequest (int workReqId);
    int updateWorkStatus (String workReqId, String workStatus);
    List<WorkRequestAndResponseVo> getWorkRequestList(int rcvId);
    List<WorkRequestAndResponseVo> getWorkRequestList(String docId);
    WorkRequestAndResponseVo getWorkRequestAndResponseList(String docId);
    void deleteDocument(String docId);

    int updateWorkRequest(WorkRequestVo vo);
}
