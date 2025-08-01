package kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;

@Mapper
public interface WorkRequestRepository {
    int insertWorkRequest (WorkRequestVo vo);
    int deleteWorkRequest (int workReqId);
    int updateWorkStatus (String workReqId, String workStatus);
    List<WorkRequestVo> getWorkRequestList(@Param("rcvId") Integer rcvId, @Param("docId") String docId);
    WorkRequestVo getWorkRequestListByUserId(String docId, String userId);
    void deleteDocument(String docId);
    String getDocIdByWorkReqId(int workReqId);

    int updateWorkRequest(WorkRequestVo vo);
}
