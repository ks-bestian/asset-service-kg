package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ApprovalLIstDto {
    private String docId;
    private String docAttrbCd;
    private String docSubtle;
    private String senderId;
    private String senderNm;
    private String docNo;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private String apvlId;
}
