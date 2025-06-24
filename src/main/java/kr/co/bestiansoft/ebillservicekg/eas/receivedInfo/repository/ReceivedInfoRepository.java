package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.repository;

import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReceivedInfoRepository {
    int insertReceivedInfo(ReceivedInfoVo vo);
    int updateReceivedInfo(UpdateReceivedInfoVo vo);
    List<ReceivedInfoVo> getReceivedInfo(String docId);
    boolean isReceipt(int rcvId);
    String getMainWorker(int rcvId);
    ReceivedInfoVo getReceivedInfoByUserId(String docId, String userId);
    ReceivedInfoVo getReceivedInfoByRcpDocId(String rcpDocId);
}
