package kr.co.bestiansoft.ebillservicekg.admin.menu.repository;

import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuVo> getMenuList(HashMap<String, Object> param);
    MenuVo getMenuDetail(Long menuId);
    int insertMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(Long id);

    List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param);
    int insertQuickMenu(QuickMenuVo quickMenuVo);
    void deleteQuickMenu(Long menuId, String userId);

}
