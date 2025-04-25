package kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkRequestRepository {
    int insertWorkRequest (WorkRequestVo vo);
    int deleteWorkRequest (String workReqId);
    int updateWorkStatus (String workReqId, String workStatus);
    List<WorkRequestVo> getWorkRequestList(String docId);
}
