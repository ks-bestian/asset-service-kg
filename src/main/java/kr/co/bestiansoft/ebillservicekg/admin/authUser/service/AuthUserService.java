package kr.co.bestiansoft.ebillservicekg.admin.authUser.service;

import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import java.util.List;

public interface AuthUserService {

    List<AuthUserVo> getAuthUserList(Long authId);
    AuthUserVo createAuthUser(AuthUserVo authUserVo);
    void deleteAuthUser(AuthUserVo authUserVo);
}
