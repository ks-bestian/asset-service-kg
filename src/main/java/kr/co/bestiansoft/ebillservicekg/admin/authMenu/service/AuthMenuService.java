package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import java.util.List;

public interface AuthMenuService {
    /*권한별 메뉴관리*/
    List<AuthMenuVo> getAuthMenuList(Long authId);
    AuthMenuVo saveAuthMenu(AuthMenuVo authMenuVo);
//    ArrayNode getMenuList(Long authId, String lang);
}
