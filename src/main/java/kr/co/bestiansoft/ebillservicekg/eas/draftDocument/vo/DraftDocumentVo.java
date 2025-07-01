package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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