package kr.co.bestiansoft.ebillservicekg.admin.menu.service;

import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
    List<MenuVo> getMenuList(HashMap<String, Object> param);
    MenuVo getMenuDetail(Long menuId);
    MenuVo createMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(Long menuId);
}
