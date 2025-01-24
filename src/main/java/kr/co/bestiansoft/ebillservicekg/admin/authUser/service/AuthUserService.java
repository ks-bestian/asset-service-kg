package kr.co.bestiansoft.ebillservicekg.admin.authUser.service;

import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;

import java.util.HashMap;
import java.util.List;

public interface AuthUserService {

    List<AuthUserVo> getAuthUserList(HashMap<String, Object> param);
    AuthUserVo createAuthUser(AuthUserVo authUserVo);
    void deleteAuthUser(List<Long> authId);
}
