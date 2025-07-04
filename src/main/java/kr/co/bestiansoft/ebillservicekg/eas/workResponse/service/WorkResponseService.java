package kr.co.bestiansoft.ebillservicekg.eas.workResponse.service;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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