package kr.co.bestiansoft.ebillservicekg.myPage.message.service;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;

import java.util.HashMap;
import java.util.List;

public interface MsgService {
    List<MsgVo> getRcvList(HashMap<String, Object> param);
    List<MsgVo> getSendList(HashMap<String, Object> param);
    MsgVo getMsgDetail(Long msgId, String lang);
    boolean sendMsg(MsgRequest msgRequest);
    int msgRcvDt(HashMap<String, Object> param);
    void deleteMsg(List<Long> msgIds);
    List<UserMemberVo> getUserMember(HashMap<String, Object> param);
}
