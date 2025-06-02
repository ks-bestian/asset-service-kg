package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ApprovalLIstDto {
    String docId;
    String docAttrbCd;
    String docSubtle;
    String senderId;
    String senderNm;
    String docNo;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    String apvlId;
}
