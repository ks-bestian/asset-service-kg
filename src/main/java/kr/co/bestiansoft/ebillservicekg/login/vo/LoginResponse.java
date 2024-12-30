package kr.co.bestiansoft.ebillservicekg.login.vo;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginResponse{

	private Account loginInfo;
    private boolean result;
    private String msg;
    private String token;

    @Builder
    public LoginResponse(boolean result, String msg, String token, Account loginInfo) {
        this.result = result;
        this.msg = msg;
        this.token = token;
        this.loginInfo = loginInfo;
    }

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
                           .loginInfo(loginInfo)
                           .build();
    }

}
