package kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class LngCodeVo extends ComDefaultVO {
    private Long lngId;
    private String lngTy1;
    private String lngTy2;
    private String lngTy3;
    private String rmk;
    private Long num;
}
