package kr.co.bestiansoft.ebillservicekg.formField.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class FormFieldVo extends ComDefaultVO {
    private Integer fieldSeq;
    private Integer formId;

    @JsonProperty("field_value")
    private String fieldValue;

    @JsonProperty("field_nm")
    private String fieldNm;

    @JsonProperty("field_type")
    private String fieldType;

    @JsonProperty("required_yn")
    private String requiredYn;
}