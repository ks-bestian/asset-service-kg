package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String apvlType;
}
