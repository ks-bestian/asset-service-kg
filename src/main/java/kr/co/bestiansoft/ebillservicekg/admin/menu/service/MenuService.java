package kr.co.bestiansoft.ebillservicekg.admin.menu.service;

import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
    List<MenuVo> getMenuList(HashMap<String, Object> param);
    MenuVo getMenuDetail(Long menuId);
    MenuVo createMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(Long menuId);
    List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param);
    QuickMenuVo createQuickMenu(QuickMenuVo quickMenuVo);
    void deleteQuickMenu(QuickMenuVo quickMenuVo);
}
