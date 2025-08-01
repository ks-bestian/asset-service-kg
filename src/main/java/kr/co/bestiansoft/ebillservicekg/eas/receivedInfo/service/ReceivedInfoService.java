package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;

public interface ReceivedInfoService {
    int insertReceivedInfo (ReceivedInfoVo vo);
    int updateReceivedInfo (UpdateReceivedInfoVo vo);
    List<ReceivedInfoVo> getReceivedInfo(String docId);
    boolean isReceipt(int docId);
    UserMemberVo getMainWorkerInfo(int rcvId);
    ReceivedInfoVo getReceivedInfoByUserId(String docId);
    ReceivedInfoVo getReceivedInfoByRcpDocId(String rcpDocId);
    void deleteDocument(String docId);
    String getDocIdByRcvId(int rcvId);
}
