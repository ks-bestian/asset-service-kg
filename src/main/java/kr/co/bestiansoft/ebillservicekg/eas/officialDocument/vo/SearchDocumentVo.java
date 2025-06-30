package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class SearchDocumentVo {
    private String userId;
    private String docSubtle;
    private String tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private LocalDateTime fromRcvDtm;
    private LocalDateTime toRcvDtm;
    String betweenRcvDtm;
    private String docNo;
    private String docTypeCd;
    private String senderNm;

    private String rcvId;

    private String searchContent;

    private LocalDateTime fromResDtm;
    private LocalDateTime toResDtm;

    private int page;
    private int size;
}
