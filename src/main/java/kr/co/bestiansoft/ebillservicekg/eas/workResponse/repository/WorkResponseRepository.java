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
    int deleteWorkRequestId(String workReqId);
    List<WorkResponseVo> getWorkResponse(@Param("rcvId") Integer rcvId, @Param("docId") String docId);
    List<WorkResponseVo> getWorkResponseByUserId(Integer workReqId, String docId);
    void deleteDocument(String docId);
}
