package kr.co.bestiansoft.ebillservicekg.myPage.message.repository;

import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MsgMapper {
    List<MsgVo> getRcvList(HashMap<String, Object> param);
    List<MsgVo> getSendList(HashMap<String, Object> param);
    MsgVo getMsgDetail(Long msgId);
    int insertMsg(MsgRequest msgRequest);
    void deleteMsg(Long msgId);
}
