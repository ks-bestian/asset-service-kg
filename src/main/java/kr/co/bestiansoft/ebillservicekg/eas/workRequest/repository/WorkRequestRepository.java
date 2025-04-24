package kr.co.bestiansoft.ebillservicekg.eas.workRequest.repository;

import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkRequestRepository {
    int insertWorkRequest (WorkRequestVo vo);
    void deleteWorkRequest (String workReqId);
    void updateWorkStatus (String workReqId, String workStatus);
}
