package kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LngCodeVo extends ComDefaultVO {
    private Long lngId;
    private String lngTy1;
    private String lngTy2;
    private String lngTy3;
    private String rmk;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private Long num;
}
