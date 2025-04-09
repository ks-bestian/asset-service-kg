package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class InsertDocumentVo {
    private String docId;
    private int aarsDocId;
    private String userId;
    private String docTypeCd;
    private String[] docAttrbCd;
    private String billId;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private String[] docLng;
    private String docSubtle;
    private String docStatusCd;
    private char digitalYn;
    private String senderId;
    private String senderNm;
    private String docNo;
    private String deptCd;
    private String jobCd;

    private String[] approvalIds;

    private String[] receiverIds;

    private String[] easFileIds;
}
