package kr.co.bestiansoft.ebillservicekg.user.login.service.impl;

import kr.co.bestiansoft.ebillservicekg.user.login.domain.Account;
import kr.co.bestiansoft.ebillservicekg.user.login.domain.LoginResponse;
import kr.co.bestiansoft.ebillservicekg.user.login.service.LoginService;
import kr.co.bestiansoft.ebillservicekg.user.login.util.SecurityInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse getLoginInfo(boolean result, String message, String token) {
        Account account = new SecurityInfoUtil().getAccount();
        
        if(account.getAccountId().length()>0){

            account.clearPassword();

            result = true;
            message = "LOGIN_SUCCESS";

        }else {
            message = "UserIdNotFound";
        }

        return LoginResponse.from(result, message, token, account);
    }
}
