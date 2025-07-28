package kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo;


import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthMenuCreate {
    private Long authId;
    private List<AuthMenuVo> authMenuVos;
}
