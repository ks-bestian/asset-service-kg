package kr.co.bestiansoft.ebillservicekg.form.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.Data;

@Data
public class FormWithFieldsVo extends ComDefaultVO {
    private Integer formId;
    private String fileId;
    private String fileNm;
    private String formTitle;
    private String formCn;
    private String pdfFileId;

    private List<FormFieldVo> fields;
}
