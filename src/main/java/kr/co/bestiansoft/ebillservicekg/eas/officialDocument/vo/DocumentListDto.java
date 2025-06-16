package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DocumentListDto {
    String docId;
    String docAttrbCd;
    String docTypeCd;
    String tmlmtYn;
    LocalDateTime tmlmtDtm;
    String docSubtle;
    String senderId;
    String senderNm;
    String docNo;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    String rcvId;
    int id;
    String status;
}
