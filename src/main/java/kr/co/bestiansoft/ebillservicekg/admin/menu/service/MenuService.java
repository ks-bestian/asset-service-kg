package kr.co.bestiansoft.ebillservicekg.admin.menu.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
    ArrayNode getMenuList(HashMap<String, Object> param);
    MenuVo getMenuDetail(Long menuId);
    MenuVo createMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(List<Long> menuId);
    List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param);
    QuickMenuVo createQuickMenu(QuickMenuVo quickMenuVo);
    void deleteQuickMenu(QuickMenuVo quickMenuVo);
}
