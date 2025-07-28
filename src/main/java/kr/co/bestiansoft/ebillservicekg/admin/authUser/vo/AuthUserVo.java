package kr.co.bestiansoft.ebillservicekg.admin.authUser.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AuthUserVo extends ComDefaultVO {
    private Long authId;
    private String userId;
    private String userNm;
    private String deptNm;
    private String polyNm;
    private List<String> userIds;
}
