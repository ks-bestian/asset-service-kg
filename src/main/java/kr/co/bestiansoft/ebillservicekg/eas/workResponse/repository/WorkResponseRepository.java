package kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository;

import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkResponseRepository {
    int insertWorkResponse (WorkResponseVo vo);
    void updateWorkContents (UpdateWorkResponseVo vo);
    void deleteWorkRequestId(String workReqId);
}
