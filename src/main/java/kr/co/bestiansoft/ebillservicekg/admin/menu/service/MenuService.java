package kr.co.bestiansoft.ebillservicekg.admin.menu.service;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;

public interface MenuService {
    ArrayNode getMenuList(HashMap<String, Object> param);
    ArrayNode getDeptMenuList(HashMap<String, Object> param);
    MenuVo getMenuDetail(Long menuId, HashMap<String, Object> param);
    MenuVo createMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(List<Long> menuId);
    List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param);
    QuickMenuVo createQuickMenu(QuickMenuVo quickMenuVo);
    void deleteQuickMenu(QuickMenuVo quickMenuVo);
}
