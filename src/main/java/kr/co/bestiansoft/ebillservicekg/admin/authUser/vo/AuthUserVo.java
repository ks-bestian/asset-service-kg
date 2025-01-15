package kr.co.bestiansoft.ebillservicekg.admin.authUser.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthUserVo extends ComDefaultVO {
    private Long authId;
    private String userId;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

    private String userNm;
    private String deptNm;
}
