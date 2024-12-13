package kr.co.bestiansoft.ebillservicekg.admin.menu.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuVo> getMenuList(HashMap<String, Object> param) {
        return menuMapper.getMenuList(param);
    }

    @Override
    public MenuVo getMenuDetail(Long menuId) {
        return menuMapper.getMenuDetail(menuId);
    }

    @Override
    public MenuVo createMenu(MenuVo menuVo) {
        menuMapper.insertMenu(menuVo);
        return menuVo;
    }

    @Override
    public int updateMenu(MenuVo menuVo) {
        return menuMapper.updateMenu(menuVo);
    }

    @Override
    public void deleteMenu(Long menuId) {
        menuMapper.deleteMenu(menuId);
    }

    @Override
    public List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param) {
        return menuMapper.getQuickMenuList(param);
    }

    @Override
    public QuickMenuVo createQuickMenu(QuickMenuVo quickMenuVo) {
//        if (quickMenuVo.getIsFavorite()) {
            menuMapper.insertQuickMenu(quickMenuVo);
//        }
        return quickMenuVo;
    }

    @Override
    public void deleteQuickMenu(QuickMenuVo quickMenuVo) {
        menuMapper.deleteQuickMenu(quickMenuVo.getMenuId(), quickMenuVo.getUserId());
    }
}
