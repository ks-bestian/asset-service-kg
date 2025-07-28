package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LinkDocumentListDto {
    private int linkId;
    private String linkedDocId;
    private String linkType;
    private LocalDateTime regDtm;
    private String regId;
    private String docSubtle;

    public LinkDocumentListDto(int linkId, String linkedDocId, String linkType, LocalDateTime regDtm, String regId, String docSubtle) {
        this.linkId = linkId;
        this.linkedDocId = linkedDocId;
        this.linkType = linkType;
        this.regDtm = regDtm;
        this.regId = regId;
        this.docSubtle = docSubtle;
    }
}
