package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.impl;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.repository.AuthMenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.AuthMenuService;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
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
    private final MenuMapper menuMapper;
    @Override
    public List<AuthMenuVo> getAuthMenuList(Long authId) {
        authMenuMapper.deleteAuthMenu(authId);

        return authMenuMapper.getAuthMenuList(authId);
    }

    @Override
    public AuthMenuCreate saveAuthMenu(AuthMenuCreate authMenuCreate) {
        List<AuthMenuVo> authMenuVos = authMenuCreate.getAuthMenuVos();
        Long authId = authMenuCreate.getAuthId();

        authMenuMapper.deleteAuthMenu(authId);

        for(AuthMenuVo authMenuVo : authMenuVos) {
            authMenuMapper.insertAuthMenu(authMenuVo);
        }
        return authMenuCreate;
    }

}
