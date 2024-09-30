package kr.co.bestiansoft.ebillservicekg.user.login.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private boolean result;
    private String msg;
    private String token;
    private Account account;

    public static LoginResponse from(boolean result, String msg) {
        return LoginResponse.builder()
                            .result(result)
                            .msg(msg)
                            .build();
    }

    public static LoginResponse from(boolean result, String msg, String token, Account loginInfo) {
        return LoginResponse.builder()
                            .result(result)
                            .msg(msg)
                            .token(token)
                            .account(loginInfo)
                            .build();
    }
    
}
