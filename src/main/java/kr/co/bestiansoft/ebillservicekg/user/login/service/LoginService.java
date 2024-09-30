package kr.co.bestiansoft.ebillservicekg.user.login.service;

import kr.co.bestiansoft.ebillservicekg.user.login.domain.LoginResponse;

public interface LoginService {

    LoginResponse getLoginInfo(boolean result, String msg, String token);

}
