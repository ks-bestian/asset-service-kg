package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service;

import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;

import java.util.List;

public interface ReceivedInfoService {
    int insertReceivedInfo (ReceivedInfoVo vo);
    int updateReceivedInfo (UpdateReceivedInfoVo vo);
    List<ReceivedInfoVo> getReceivedInfo(String docId);
}
