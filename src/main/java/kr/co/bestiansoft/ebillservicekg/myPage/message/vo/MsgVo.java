package kr.co.bestiansoft.ebillservicekg.myPage.message.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MsgVo extends ComDefaultVO {
    private Long msgId;
    private LocalDateTime cncDt;
    private String msgCn;
    private String msgSj;
    private LocalDateTime rcvDt;
    private String rcvId;
    private LocalDateTime sendDt;
    private String sendDate;
    private String sendId;
    private String fileGroupId;
    private String msgDiv;
    private String msgGroupId;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private String rcvNm;
    private String sendNm;

    private List<String> rcvIds;
}
