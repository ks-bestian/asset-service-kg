package kr.co.bestiansoft.ebillservicekg.myPage.message.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.message.repository.MsgMapper;
import kr.co.bestiansoft.ebillservicekg.myPage.message.service.MsgService;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MsgServiceImpl implements MsgService {

    private final MsgMapper msgMapper;

    @Override
    public List<MsgVo> getRcvList(HashMap<String, Object> param) {
        return msgMapper.getRcvList(param);
    }

    @Override
    public List<MsgVo> getSendList(HashMap<String, Object> param) {
        return msgMapper.getSendList(param);
    }

    @Override
    public MsgVo getMsgDetail(Long msgId) {
        return msgMapper.getMsgDetail(msgId);
    }

    @Override
    public MsgRequest sendMsg(MsgRequest msgRequest) {
        List<String> rcvIds = msgRequest.getRcvIds();
        msgRequest.setMsgGroupId(StringUtil.getUUUID());

        for (String rcvId : rcvIds) {
            msgRequest.setRcvId(rcvId);
            msgRequest.setMsgDiv("R");
            msgMapper.insertMsg(msgRequest);
        }

        for (String rcvId : rcvIds) {
            msgRequest.setRcvId(rcvId);
            msgRequest.setMsgDiv("S");
            msgMapper.insertMsg(msgRequest);
        }
        return msgRequest;
    }

    @Override
    public void deleteMsg(List<Long> msgIds) {
        for (Long id : msgIds) {
            msgMapper.deleteMsg(id);
        }
    }
}
