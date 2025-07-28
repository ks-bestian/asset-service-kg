package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DocumentListDto {
    private String docId;
    private String docAttrbCd;
    private String docTypeCd;
    private String tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private String docSubtle;
    private String senderId;
    private String senderNm;
    private String docNo;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private LocalDateTime resDtm;
    private String rcvId;
    private int rspnsId;
    private String externalYn;
    private String aarsDocId;

    private String resUserId;
}
