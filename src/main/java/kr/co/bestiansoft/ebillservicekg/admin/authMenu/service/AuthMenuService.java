package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import java.util.List;

public interface AuthMenuService {
    /*Permanent Menu management*/
    List<AuthMenuVo> getAuthMenuList(Long authId);
    AuthMenuCreate saveAuthMenu(AuthMenuCreate authMenuCreate);
//    ArrayNode getMenuList(Long authId, String lang);
}
