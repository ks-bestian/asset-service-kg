package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OfficialDocumentVo {
    private String docId;
    private int aarsDocId;
    private String userId;
    private String docTypeCd;
    private String docAttrbCd;
    private String billId;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private String docLng;
    private String docSubtle;
    private String docStatusCd;
    private char digitalYn;
    private String senderId;
    private String senderNm;
    private String senderDeptCd;
    private String docNo;
    private LocalDateTime regDtm;
    private String externalYn;
    private String regId;
    private LocalDateTime regDt;

    @Builder
    public OfficialDocumentVo(String docId, int aarsDocId, String userId, String docTypeCd, String docAttrbCd, String billId, char tmlmtYn, LocalDateTime tmlmtDtm, String docLng, String docSubtle, String docStatusCd, char digitalYn, String senderId, String senderNm, String senderDeptCd, String docNo, LocalDateTime regDtm, String externalYn, String regId, LocalDateTime regDt) {
        this.docId = docId;
        this.aarsDocId = aarsDocId;
        this.userId = userId;
        this.docTypeCd = docTypeCd;
        this.docAttrbCd = docAttrbCd;
        this.billId = billId;
        this.tmlmtYn = tmlmtYn;
        this.tmlmtDtm = tmlmtDtm;
        this.docLng = docLng;
        this.docSubtle = docSubtle;
        this.docStatusCd = docStatusCd;
        this.digitalYn = digitalYn;
        this.senderId = senderId;
        this.senderNm = senderNm;
        this.senderDeptCd = senderDeptCd;
        this.docNo = docNo;
        this.regDtm = regDtm;
        this.externalYn = externalYn;
        this.regId = regId;
        this.regDt = regDt;
    }
}