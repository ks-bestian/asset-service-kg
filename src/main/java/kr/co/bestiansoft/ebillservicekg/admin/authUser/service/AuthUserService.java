package kr.co.bestiansoft.ebillservicekg.admin.authUser.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;

public interface AuthUserService {

    List<AuthUserVo> getAuthUserList(HashMap<String, Object> param);
    AuthUserVo createAuthUser(AuthUserVo authUserVo);
    void deleteAuthUser(List<Long> authId);
}
