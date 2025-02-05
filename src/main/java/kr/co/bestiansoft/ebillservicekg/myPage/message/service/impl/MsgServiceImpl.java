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
    public MsgVo getMsgDetail(Long msgId) {
        return msgMapper.selectMsg(msgId);
    }

    @Override
    public MsgRequest sendMsg(MsgRequest msgRequest) {
        msgRequest.setSendId(new SecurityInfoUtil().getAccountId());
        String fileGroupId = null;

        if(msgRequest.getFiles() != null) {
            fileGroupId = comFileService.saveFile(msgRequest.getFiles());
        }

        msgRequest.setFileGroupId(fileGroupId);

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
        fileGroupId = null;
        return msgRequest;
    }

    @Override
    public int msgRcvDt(HashMap<String, Object> param) {
        Long msgId = ((Integer) param.get("msgId")).longValue();
        MsgVo msg = msgMapper.selectMsg(msgId);
        if(msg.getRcvDt() != null) {
            msgMapper.updateRcvDt(param);
        }
        return 0;
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
