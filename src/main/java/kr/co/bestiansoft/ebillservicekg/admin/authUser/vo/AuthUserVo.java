package kr.co.bestiansoft.ebillservicekg.admin.authUser.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuthUserVo extends ComDefaultVO {
    private Long authId;
    private String userId;
    private String userNm;
    private String deptNm;
    private String polyNm;
    private List<String> userIds;
}
