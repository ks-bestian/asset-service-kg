package kr.co.bestiansoft.ebillservicekg.myPage.message.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MsgServiceImpl implements MsgService {

	private final SimpMessagingTemplate messagingTemplate; // WebSocket Message transmission

    private final MsgMapper msgMapper;
    private final ComFileService comFileService;

    /**
     * Retrieves a list of received messages for the authenticated user.
     * The method extracts the account ID of the authenticated user using the {@code SecurityInfoUtil} utility
     * and adds it to the provided parameters before querying the database for the list of messages.
     *
     * @param param a HashMap containing the query parameters for retrieving the received messages. The method
     *              adds the key "rcvId", which corresponds to the authenticated user's account ID.
     * @return a List of {@code MsgVo} objects representing the received messages that match the given parameters.
     */
    @Override
    public List<MsgVo> getRcvList(HashMap<String, Object> param) {
        param.put("rcvId", new SecurityInfoUtil().getAccountId());
        return msgMapper.selectListRcv(param);
    }

    /**
     * Retrieves a list of sent messages for the authenticated user.
     * The method extracts the account ID of the authenticated user using the {@code SecurityInfoUtil} utility
     * and adds it to the provided parameters before querying the database for the list of sent messages.
     *
     * @param param a HashMap containing the query parameters for retrieving the sent messages. The method
     *              adds the key "sendId", which corresponds to the authenticated user's account ID.
     * @return a List of {@code MsgVo} objects representing the sent messages that match the given parameters.
     */
    @Override
    public List<MsgVo> getSendList(HashMap<String, Object> param) {
        param.put("sendId", new SecurityInfoUtil().getAccountId());

        return msgMapper.selectListSend(param);
    }

    /**
     * Retrieves the details of a specific message based on the provided message ID and language.
     *
     * @param msgId the unique identifier of the message to retrieve
     * @param lang the language code specifying the language for the message details
     * @return a {@code MsgVo} object containing the details of the specified message
     */
    @Override
    public MsgVo getMsgDetail(Long msgId, String lang) {
        return msgMapper.selectMsg(msgId, lang);
    }

    /**
     * Sends a message to a list of recipients.
     * This method processes the provided message request by setting the sender ID, handling file attachments,
     * generating unique identifiers for the message group, and persisting message data into the database.
     * Notifications are sent to recipients in real-time via WebSocket.
     *
     * @param msgRequest the message request containing the details of the message such as content, subject,
     *                   recipient IDs, sender ID, and optional attached files
     * @return true if the message was successfully processed and sent; false otherwise
     */
    @Override
    public boolean sendMsg(MsgRequest msgRequest) {
        msgRequest.setSendId(new SecurityInfoUtil().getAccountId());

        if (msgRequest.getFiles() != null) {
            String fileGroupId = comFileService.saveFileList(msgRequest.getFiles());
            msgRequest.setFileGroupId(fileGroupId);
        }

        List<String> rcvIds = msgRequest.getRcvIds();
        msgRequest.setMsgGroupId(StringUtil.getUUUID());

        for (String rcvId : rcvIds) {
            msgRequest.setRcvId(rcvId);
            msgRequest.setMsgDiv("R");
            msgMapper.insertMsg(msgRequest);

            // Real -time notification transmission through WebSOCKET
            messagingTemplate.convertAndSend("/topic/messages/" + rcvId, "A new message arrived!");

            msgRequest.setMsgDiv("S");
            msgMapper.insertMsg(msgRequest);
        }

//        fileGroupId = null;
//        return msgRequest;
        return true;
    }

    /**
     * Updates the received date for a specific message based on the provided parameters.
     * This method calls the data persistence layer to perform the update operation in the database.
     *
     * @param param a HashMap containing the parameters required for updating the received date. It is expected
     *              to include necessary identifiers or data attributes to identify the target message.
     * @return the number of rows affected by the update operation in the database.
     */
    @Override
    public int msgRcvDt(HashMap<String, Object> param) {
        return msgMapper.updateRcvDt(param);
    }

    /**
     * Deletes the messages corresponding to the provided list of message IDs.
     * This method iterates through each message ID in the list and invokes the
     * data persistence layer to remove the message from the database.
     *
     * @param msgIds a list of unique message identifiers to be deleted
     */
    @Override
    public void deleteMsg(List<Long> msgIds) {
        for (Long id : msgIds) {
            msgMapper.deleteMsg(id);
        }
    }

    /**
     * Retrieves a list of user members based on the provided parameters.
     * This method queries the data persistence layer to fetch user member information.
     *
     * @param param a HashMap containing the query parameters for retrieving user members.
     *              These parameters may include filters or identifiers to customize the result.
     * @return a List of {@code UserMemberVo} objects representing the user members that match the given parameters.
     */
    @Override
    public List<UserMemberVo> getUserMember(HashMap<String, Object> param) {
        return msgMapper.selectUserMember(param);
    }
}
