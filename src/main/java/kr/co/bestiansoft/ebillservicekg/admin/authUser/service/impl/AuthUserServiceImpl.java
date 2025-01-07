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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthUserServiceImpl implements AuthUserService {

    private static final Logger logger = LoggerFactory.getLogger(AuthMenuServiceImpl.class);


    private final AuthUserMapper authUserMapper;
    @Override
    public List<AuthUserVo> getAuthUserList(Long authId) {
        return authUserMapper.getAuthUserList(authId);
    }

    @Override
    public AuthUserVo createAuthUser(AuthUserVo authUserVo) {
        String[] userIds = authUserVo.getUserIds();
        Long authId = authUserVo.getAuthId();

        for(String userId : userIds) {
            if(!authUserMapper.existsByAuthIdAndUserId(authId, userId)){
                authUserMapper.createAuthUser(AuthUserVo.builder().authId(authId).userId(userId).build());
            } else {
                logger.info("ComAuthUserEntity already exists, skipping save: authId:"+authId+" userId:"+userId);

            }
        }
        authUserMapper.createAuthUser(authUserVo);
        return authUserVo;
    }

    @Override
    public void deleteAuthUser(AuthUserVo authUserVo) {
        authUserMapper.deleteAuthMenu(authUserVo);
    }
}
