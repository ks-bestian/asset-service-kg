package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LinkDocumentVo {
    private int linkId;
    private String fromDocumentId;
    private String toDocumentId;
    private String linkType;
    private LocalDateTime regDt;
    private String regId;
}