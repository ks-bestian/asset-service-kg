package kr.co.bestiansoft.ebillservicekg.myPage.message.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.message.repository.MsgMapper;
import kr.co.bestiansoft.ebillservicekg.myPage.message.service.MsgService;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MsgServiceImpl implements MsgService {

	private final SimpMessagingTemplate messagingTemplate; // WebSocket 메시지 전송

    private final MsgMapper msgMapper;
    private final ComFileService comFileService;

    @Override
    public List<MsgVo> getRcvList(HashMap<String, Object> param) {
        param.put("rcvId", new SecurityInfoUtil().getAccountId());
        return msgMapper.selectListRcv(param);
    }

    @Override
    public List<MsgVo> getSendList(HashMap<String, Object> param) {
        param.put("sendId", new SecurityInfoUtil().getAccountId());

        return msgMapper.selectListSend(param);
    }

    @Override
    public MsgVo getMsgDetail(Long msgId, String lang) {
        return msgMapper.selectMsg(msgId, lang);
    }

    @Override
    public MsgRequest sendMsg(MsgRequest msgRequest) {
        msgRequest.setSendId(new SecurityInfoUtil().getAccountId());
        String fileGroupId = null;

        if (msgRequest.getFiles() != null) {
            fileGroupId = comFileService.saveFile(msgRequest.getFiles());
        }

        msgRequest.setFileGroupId(fileGroupId);

        List<String> rcvIds = msgRequest.getRcvIds();
        msgRequest.setMsgGroupId(StringUtil.getUUUID());

        for (String rcvId : rcvIds) {
            msgRequest.setRcvId(rcvId);
            msgRequest.setMsgDiv("R");
            msgMapper.insertMsg(msgRequest);

            // WebSocket을 통해 실시간 알림 전송
            messagingTemplate.convertAndSend("/topic/messages/" + rcvId, "새 쪽지가 도착했습니다!");

        }

        for (String rcvId : rcvIds) {
            msgRequest.setRcvId(rcvId);
            msgRequest.setMsgDiv("S");
            msgMapper.insertMsg(msgRequest);
        }
        fileGroupId = null;
        return msgRequest;
    }

    @Override
    public int msgRcvDt(HashMap<String, Object> param) {
        return msgMapper.updateRcvDt(param);
    }

    @Override
    public void deleteMsg(List<Long> msgIds) {
        for (Long id : msgIds) {
            msgMapper.deleteMsg(id);
        }
    }

    @Override
    public List<UserMemberVo> getUserMember(HashMap<String, Object> param) {
        return msgMapper.selectUserMember(param);
    }
}
