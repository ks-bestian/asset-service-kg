package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class LinkDocumentVo {
    private int linkId;
    private String fromDocId;
    private String toDocId;
    private String linkType;
    private LocalDateTime regDtm;
    private String regId;

    @Builder
    public LinkDocumentVo(int linkId, String fromDocId, String toDocId, String linkType, LocalDateTime regDtm, String regId) {
        this.linkId = linkId;
        this.fromDocId = fromDocId;
        this.toDocId = toDocId;
        this.linkType = linkType;
        this.regDtm = regDtm;
        this.regId = regId;
    }
}