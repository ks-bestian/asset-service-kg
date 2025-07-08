package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.impl;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.repository.AuthMenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.AuthMenuService;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthMenuServiceImpl implements AuthMenuService {

    private final AuthMenuMapper authMenuMapper;

    /**
     * Retrieves a list of authorized menu items associated with a specific authorization ID.
     *
     * @param authId the authorization ID for which to fetch the menu list
     * @return a list of AuthMenuVo objects representing the authorized menu items
     */
    @Override
    public List<AuthMenuVo> getAuthMenuList(Long authId) {
        return authMenuMapper.selectListAuthMenu(authId);
    }

    /**
     * Saves the authorization menu configuration by removing any existing menus
     * associated with the given authorization and inserting the new provided menu items.
     *
     * @param authMenuCreate the object containing authorization ID and a list of menu items to be saved
     * @return the updated AuthMenuCreate object after saving the menu items
     */
    @Override
    public AuthMenuCreate saveAuthMenu(AuthMenuCreate authMenuCreate) {
        List<AuthMenuVo> authMenuVos = authMenuCreate.getAuthMenuVos();
        Long authId = authMenuCreate.getAuthId();

        authMenuMapper.deleteAuthMenu(authId);

        for(AuthMenuVo authMenuVo : authMenuVos) {
            authMenuVo.setRegId(new SecurityInfoUtil().getAccountId());
            authMenuMapper.insertAuthMenu(authMenuVo);
        }
        return authMenuCreate;
    }

}
