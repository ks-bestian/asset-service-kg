package kr.co.bestiansoft.ebillservicekg.myPage.message.repository;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MsgMapper {
    List<MsgVo> selectListRcv(HashMap<String, Object> param);
    List<MsgVo> selectListSend(HashMap<String, Object> param);
    MsgVo selectMsg(Long msgId, String lang);
    int insertMsg(MsgRequest msgRequest);
    int updateRcvDt(HashMap<String, Object> param);
    void deleteMsg(Long msgId);
    List<UserMemberVo> selectUserMember(HashMap<String, Object> param);
}
