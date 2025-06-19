package kr.co.bestiansoft.ebillservicekg.eas.draftData.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DraftDataVo {
    private String dataSeq;
    private int aarsDocId;
    private int formId;
    private int fieldSeq;
    private String dataCn;

    @Builder
    public DraftDataVo(String dataSeq, int aarsDocId, int formId, int fieldSeq, String dataCn) {
        this.dataSeq = dataSeq;
        this.aarsDocId = aarsDocId;
        this.formId = formId;
        this.fieldSeq = fieldSeq;
        this.dataCn = dataCn;
    }
}
