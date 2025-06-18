package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchDocumentVo {
    private String userId;
    private String docSubtle;
    private String tmlmtYn;
    private String tmlmtDtm;
    private String fromRcvDtm;
    private String toRcvDtm;
    private String docNo;
    private String docTypeCd;
    private String senderNm;

    private String rcvId;
}
