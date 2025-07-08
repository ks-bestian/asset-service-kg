package kr.co.bestiansoft.ebillservicekg.admin.menu.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.menu.domain.MenuHierarchy;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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

    /**
     * Retrieves a hierarchical menu structure based on the given parameters.
     *
     * @param param a HashMap containing parameters for fetching the menu list.
     *              Expected keys include:
     *              - "lang" (String): The language code to filter the menu items.
     *              - "type" (String): The type of menu structure to be generated.
     * @return an ArrayNode representing the hierarchical menu structure in JSON format.
     */
    @Override
    public ArrayNode getMenuList(HashMap<String, Object> param) {
        String lang = (String) param.get("lang");
        String type = (String) param.get("type");

        List<MenuVo> list = menuMapper.selectListMenu(param);
        MenuHierarchy mh = new MenuHierarchy();
        mh.buildMenuHierarchy(list, lang);
        ArrayNode rootNode = mh.getMenuJson(type);
        return rootNode;
    }

    /**
     * Retrieves a hierarchical department menu structure based on the given parameters.
     * addition(20250210 Jinho Cho)
     * @param param a HashMap containing parameters required for fetching the department menu list.
     *              Expected keys include:
     *              - "lang" (String): The language code to filter the menu items.
     *              - "type" (String): The type of menu structure to be generated.
     *              Note: The department code is automatically added to the parameters using SecurityInfoUtil.
     * @return an ArrayNode representing the hierarchical department menu structure in JSON format.
     */
    @Override
    public ArrayNode getDeptMenuList(HashMap<String, Object> param) {
        String lang = (String) param.get("lang");
        String type = (String) param.get("type");

        param.put("deptCd", new SecurityInfoUtil().getDeptCd());
        List<MenuVo> list = menuMapper.selectListMenu(param);
        MenuHierarchy mh = new MenuHierarchy();
        mh.buildMenuHierarchy(list, lang);
        ArrayNode rootNode = mh.getMenuJson(type);
        return rootNode;
    }

    /**
     * Retrieves detailed information about a specific menu.
     *
     * @param menuId the unique identifier of the menu to be retrieved.
     * @param param a HashMap containing additional parameters for
     */
    @Override
    public MenuVo getMenuDetail(Long menuId, HashMap<String, Object> param) {
        return menuMapper.selectMenu(menuId, (String) param.get("lang"));
    }

    /**
     * Creates a new menu entry in the system.
     *
     * @param menuVo the MenuVo object containing information about the menu to be created,
     *               including its name, parent menu ID, order, and other details.
     * @return the MenuVo object that was created, including any additional fields that were
     *         populated during the creation process (e.g., generated menu ID).
     */
    @Override
    public MenuVo createMenu(MenuVo menuVo) {
        menuMapper.insertMenu(menuVo);
        return menuVo;
    }

    /**
     * Updates an existing menu with the provided details.
     *
     * @param menuVo the MenuVo object containing the updated menu details,
     *               including fields such as menu ID, name, parent menu ID, order, and other attributes.
     * @return the number of rows affected in the database as a*/
    @Override
    public int updateMenu(MenuVo menuVo) {
        return menuMapper.updateMenu(menuVo);
    }

    /**
     * Deletes multiple menus identified by their unique IDs.
     *
     * @param menuIds a list of unique identifiers for the menus to be deleted.
     *                Each ID corresponds to a menu entry that should be removed.
     */
    @Override
    public void deleteMenu(List<Long> menuIds) {
        for(Long menuId : menuIds) {
            menuMapper.deleteMenu(menuId);
        }
    }

    /**
     * Retrieves a list of quick menu entries based on the given parameters.
     * Adds the current user's account ID to the parameters before querying.
     *
     * @param param a HashMap containing parameters for fetching the quick menu list.
     *              Additional key "userId" is automatically added with the current user's account ID.
     * @return a List of QuickMenuVo objects representing the quick menu entries.
     */
    @Override
    public List<QuickMenuVo> getQuickMenuList(HashMap<String, Object> param) {
    	param.put("userId", new SecurityInfoUtil().getAccountId());
        return menuMapper.selectListQuickMenu(param);
    }

    /**
     * Creates a new quick menu entry in the system.
     *
     * @param quickMenuVo the QuickMenuVo object containing information about the quick menu to be created,
     *                    including details such as name, menu ID, and other attributes.
     *                    The method automatically assigns the current user's account ID as the userId and regId.
     * @return the QuickMenuVo object that was created, including any additional fields populated during
     *         the creation process.
     */
    @Override
    public QuickMenuVo createQuickMenu(QuickMenuVo quickMenuVo) {
    	quickMenuVo.setUserId(new SecurityInfoUtil().getAccountId());
    	quickMenuVo.setRegId(new SecurityInfoUtil().getAccountId());
//        if (quickMenuVo.getIsFavorite()) {
            menuMapper.insertQuickMenu(quickMenuVo);
//        }
        return quickMenuVo;
    }

    /**
     * Deletes a quick menu entry based on the provided QuickMenuVo object.
     * The method automatically assigns the current user's account ID to the quickMenuVo
     * and uses it along with the menu ID to delete the corresponding quick menu entry.
     *
     * @param quickMenuVo the QuickMenuVo object containing details about the quick menu to be deleted.
     *                    Must include the menu ID of the entry to be deleted.
     */
    @Override
    public void deleteQuickMenu(QuickMenuVo quickMenuVo) {
    	quickMenuVo.setUserId(new SecurityInfoUtil().getAccountId());
        menuMapper.deleteQuickMenu(quickMenuVo.getMenuId(), quickMenuVo.getUserId());
    }
}
