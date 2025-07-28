package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;

public interface AuthMenuService {
    /*Permanent Menu management*/
    List<AuthMenuVo> getAuthMenuList(Long authId);
    AuthMenuCreate saveAuthMenu(AuthMenuCreate authMenuCreate);
//    ArrayNode getMenuList(Long authId, String lang);
}
