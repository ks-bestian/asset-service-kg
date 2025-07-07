package kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkResponseRepository {
    int insertWorkResponse (WorkResponseVo vo);
    int updateWorkContents (UpdateWorkResponseVo vo);

    List<WorkResponseVo> getWorkResponseByRcvId(@Param("rcvId") Integer rcvId, @Param("docId") String docId);
    List<WorkResponseVo> getWorkResponseByUserId(Integer workReqId, String userId);
    void deleteDocument(String docId);
    List<WorkResponseVo> getWorkResponses( int workReqId);
    WorkResponseVo getResponse(int rspnsId);

    List<String> getRespondedUsers(int workReqId);
    int deleteWorkRequestId(int workReqId);
}
