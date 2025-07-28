package kr.co.bestiansoft.ebillservicekg.myPage.message.vo;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

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
    private String rcvNm;
    private String sendNm;

    private List<String> rcvIds;
}
