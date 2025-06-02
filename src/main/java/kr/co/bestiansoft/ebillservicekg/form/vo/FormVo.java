package kr.co.bestiansoft.ebillservicekg.form.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class FormVo extends ComDefaultVO {
    private Integer formId;
    private String fileId;
    private String fileNm;
    private String formTitle;
    private String formCn;
    private String pdfFileId;
}
