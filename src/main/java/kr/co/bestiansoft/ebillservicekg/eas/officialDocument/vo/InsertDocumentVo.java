package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDate tmlmtDtm;
    private String[] docLng;
    private String docSubtle;
    private String docStatusCd;
    private char digitalYn;
    private String senderId;
    private String senderNm;
    private String docNo;
    private String senderDeptCd;
    private String jobCd;
    private String regDtm;
    private char externalYn;

    private String fromDocId;
    private int rcvId;
    private String[] alreadyUploadFiles;

    private String[] approvalIds;

    private List<Map<String, String>> receiverIds;

    private String[] easFileIds;
}
