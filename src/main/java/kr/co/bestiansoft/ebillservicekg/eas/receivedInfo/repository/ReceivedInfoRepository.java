package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.repository;

import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceivedInfoRepository {
    int insertReceivedInfo(ReceivedInfoVo vo);
}
