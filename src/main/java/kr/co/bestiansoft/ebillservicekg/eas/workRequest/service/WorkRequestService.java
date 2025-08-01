package kr.co.bestiansoft.ebillservicekg.eas.workRequest.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;

public interface WorkRequestService {
    int insertWorkRequest(WorkRequestVo vo);
    int deleteWorkRequest (int workReqId);
    int updateWorkStatus (String workReqId, String workStatus);
    List<WorkRequestAndResponseVo> getWorkRequestList(int rcvId);
    List<WorkRequestAndResponseVo> getWorkRequestList(String docId);
    WorkRequestAndResponseVo getWorkRequestAndResponseList(String docId);
    void deleteDocument(String docId);
    String getDocIdByWorkReqId(int workReqId);

    int updateWorkRequest(WorkRequestVo vo);
}
