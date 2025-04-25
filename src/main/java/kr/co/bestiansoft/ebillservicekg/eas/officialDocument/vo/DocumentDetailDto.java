package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DocumentDetailDto {
    String docId;
    String aarsFileId;
    String docTypeCd;
    String docAttrbCd;
    String billId;
    String billNo;
    String tmlmtYn;
    LocalDateTime tmlmtDtm;
    String docLng;
    String docSubtle;
    String digitalYn;
    String senderId;
    String senderNm;
    String deptCd;
    String docNo;
    LocalDateTime regDtm;
}
