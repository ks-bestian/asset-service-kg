package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;

public interface WorkResponseService {
    int insertWorkResponse(WorkResponseVo vo);
    int updateWorkResponse(UpdateWorkResponseVo vo);
    void deleteWorkRequestId(int workReqId);
    List<WorkResponseVo> getWorkResponse(int rcvId);
    List<WorkResponseVo> getWorkResponse(String docId);
    List<WorkResponseVo> getWorkResponseByUserId(int workReqId);
    void updateReadDtm(int rspnsId);
    void deleteDocument(String docId);
    List<WorkResponseVo> getWorkResponses(int workReqId);
    List<String> getRespondedUsers(int workReqId);
}