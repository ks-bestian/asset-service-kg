package kr.co.bestiansoft.ebillservicekg.admin.comCode.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ComCodeVo extends ComDefaultVO{
    private Integer grpCode;
    private String grpCodeNm1;
    private String grpCodeNm2;
    private String grpCodeNm3;
    private String grpCodeNm;
    private String useYn;
    private String rmk;
}
