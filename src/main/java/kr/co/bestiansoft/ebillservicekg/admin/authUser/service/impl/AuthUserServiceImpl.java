package kr.co.bestiansoft.ebillservicekg.admin.authUser.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.impl.AuthMenuServiceImpl;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.repository.AuthUserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.service.AuthUserService;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthUserServiceImpl implements AuthUserService {


    private final AuthUserMapper authUserMapper;
    @Override
    public List<AuthUserVo> getAuthUserList(HashMap<String, Object> param) {
        return authUserMapper.selectListAuthUser(param);
    }

    @Override
    public AuthUserVo createAuthUser(AuthUserVo authUserVo) {

        authUserMapper.createAuthUser(authUserVo);

        return authUserVo;
    }

    @Override
    public void deleteAuthUser(AuthUserVo authUserVo) {
        authUserMapper.deleteAuthMenu(authUserVo);
    }
}
