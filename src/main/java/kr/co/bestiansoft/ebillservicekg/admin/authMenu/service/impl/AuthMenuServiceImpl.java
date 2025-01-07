package kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.impl;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.repository.AuthMenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.AuthMenuService;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthMenuServiceImpl implements AuthMenuService {

    private final AuthMenuMapper authMenuMapper;
    @Override
    public List<AuthMenuVo> getAuthMenuList(Long authId) {
        authMenuMapper.deleteAuthMenu(authId);

        return authMenuMapper.getAuthMenuList(authId);
    }

    @Override
    public AuthMenuVo saveAuthMenu(AuthMenuVo authMenuVo) {
        authMenuMapper.insertAuthMenu(authMenuVo);
        return authMenuVo;
    }
}
