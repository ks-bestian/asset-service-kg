package kr.co.bestiansoft.ebillservicekg.admin.lawInfo.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LawInfoVo extends ComDefaultVO {
    private Long lawId;
    private String lawNm1;
    private String lawNm2;
    private String lawNm3;
    private String lawDeptCd;
    private String useYn;
    private String rmk;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private String lawNm;
}
