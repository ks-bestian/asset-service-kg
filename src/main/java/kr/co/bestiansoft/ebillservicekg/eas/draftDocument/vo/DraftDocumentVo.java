package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class DraftDocumentVo {
    private int aarsDocId;
    private int formId;
    private String aarsFileId;
    private String aarsStatusCd;
    private String aarsPdfFileId;
    private String regId;
    private LocalDateTime regDt;

    @Builder
    public DraftDocumentVo(int aarsDocId, int formId, String aarsFileId, String aarsStatusCd, String aarsPdfFileId, String regId, LocalDateTime regDt) {
        this.aarsDocId = aarsDocId;
        this.formId = formId;
        this.aarsFileId = aarsFileId;
        this.aarsStatusCd = aarsStatusCd;
        this.aarsPdfFileId = aarsPdfFileId;
        this.regId = regId;
        this.regDt = regDt;
    }
}