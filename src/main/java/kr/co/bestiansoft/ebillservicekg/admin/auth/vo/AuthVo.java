package kr.co.bestiansoft.ebillservicekg.admin.auth.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthVo extends ComDefaultVO {
    private Long authId;
    private String authNm1;
    private String authNm2;
    private String authNm3;
    private String useYn;
    private Long ord;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
