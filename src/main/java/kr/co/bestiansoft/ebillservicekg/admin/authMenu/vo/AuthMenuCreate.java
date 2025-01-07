package kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthMenuCreate {
    private Long authId;
    private List<AuthMenuVo> authMenuVoList;
}
