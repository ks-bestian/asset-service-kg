package kr.co.bestiansoft.ebillservicekg.myPage.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgRequest {
    private LocalDateTime cncDt;
    private String msgCn;
    private String msgSj;
    private LocalDateTime rcvDt;
    private List<String> rcvIds;
    private LocalDateTime sendDt;
    private String sendId;
    private String fileGroupId;
    private String msgDiv;
    private String msgGroupId;
    private String rcvId;
}
