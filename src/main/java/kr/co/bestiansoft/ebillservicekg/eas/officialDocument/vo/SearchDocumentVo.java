package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class SearchDocumentVo {
    private String userId;
    private String docSubtle;
    private String tmlmtYn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tmlmtDtm;
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
    String betweenResDtm;

    private int page;
    private int size;
}
