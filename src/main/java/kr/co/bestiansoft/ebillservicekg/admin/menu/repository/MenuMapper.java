package kr.co.bestiansoft.ebillservicekg.admin.menu.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;

@Mapper
public interface MenuMapper {

    List<MenuVo> selectListMenu(HashMap<String, Object> param);
    MenuVo selectMenu(Long menuId, String lang);
    int insertMenu(MenuVo menuVo);
    int updateMenu(MenuVo menuVo);
    void deleteMenu(Long id);

    List<QuickMenuVo> selectListQuickMenu(HashMap<String, Object> param);
    int insertQuickMenu(QuickMenuVo quickMenuVo);
    void deleteQuickMenu(Long menuId, String userId);

}
