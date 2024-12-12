package kr.co.bestiansoft.ebillservicekg.admin.ccof.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CcofVo extends ComDefaultVO {
    private String userId;
    private String deptCd;
    private Integer ord;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
