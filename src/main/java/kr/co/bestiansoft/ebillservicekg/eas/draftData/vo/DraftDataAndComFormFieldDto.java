package kr.co.bestiansoft.ebillservicekg.eas.draftData.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DraftDataAndComFormFieldDto {
    private int dataSeq;
    private int aarsDocId;
    private int formId;
    private int fieldSeq;
    private String dataCn;
    private String fieldValue;
    private String fieldType;

    @Builder
    public DraftDataAndComFormFieldDto(int dataSeq, int aarsDocId, int formId, int fieldSeq, String dataCn, String fieldValue, String fieldType) {
        this.dataSeq = dataSeq;
        this.aarsDocId = aarsDocId;
        this.formId = formId;
        this.fieldSeq = fieldSeq;
        this.dataCn = dataCn;
        this.fieldValue = fieldValue;
        this.fieldType = fieldType;
    }
}
