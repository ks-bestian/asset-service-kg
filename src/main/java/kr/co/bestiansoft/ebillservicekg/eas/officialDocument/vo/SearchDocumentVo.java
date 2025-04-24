package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchDocumentVo {
    String userId;
    String docSubtle;
    String tmlmtYn;
    String tmlmtDtm;
    String fromRcvDtm;
    String toRcvDtm;
    String docNo;
    String docTypeCd;
}
