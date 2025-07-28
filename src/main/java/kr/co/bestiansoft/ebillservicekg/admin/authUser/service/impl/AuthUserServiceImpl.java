package kr.co.bestiansoft.ebillservicekg.admin.authUser.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.authUser.repository.AuthUserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.service.AuthUserService;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthUserServiceImpl implements AuthUserService {


    private final AuthUserMapper authUserMapper;

    /**
     * Retrieves a list of authorized user information based on the provided parameters.
     *
     * @param param a map of parameters used to filter the list of authorized users
     * @return a list of AuthUserVo objects containing authorized user details
     */
    @Override
    public List<AuthUserVo> getAuthUserList(HashMap<String, Object> param) {
        return authUserMapper.selectListAuthUser(param);
    }

    /**
     * Creates a new authorization user configuration by first deleting any existing configuration
     * for the given authorization ID and then creating new user associations for the provided authorization.
     *
     * @param authUserVo the authorization user object containing the authorization ID and user details
     *                   to be updated or created
     * @return the updated <code>AuthUserVo</code> object after creating new user associations
     */
    @Override
    public AuthUserVo createAuthUser(AuthUserVo authUserVo) {
        authUserMapper.deleteAuthUser(authUserVo.getAuthId());
        authUserVo.setRegId(new SecurityInfoUtil().getAccountId());
        for(String userId : authUserVo.getUserIds()) {
            authUserVo.setUserId(userId);
            authUserMapper.createAuthUser(authUserVo);
        }

        return authUserVo;
    }

    /**
     * Deletes authorization user configurations for a list of specified authorization IDs.
     *
     * @param authId a list of authorization IDs for which the user configurations should be deleted
     */
    @Override
    public void deleteAuthUser(List<Long> authId) {
        for (Long id : authId) {
            authUserMapper.deleteAuthUser(id);
        }

    }
}
