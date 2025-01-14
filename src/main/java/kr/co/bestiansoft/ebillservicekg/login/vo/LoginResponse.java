package kr.co.bestiansoft.ebillservicekg.login.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import lombok.Builder;
import lombok.Data;

@Data
public class LoginResponse{

	private Account loginInfo;
    private boolean result;
    private String msg;
    private String token;
    private List<ComCodeDetailVo> comCodes;

    @Builder
    public LoginResponse(boolean result, String msg, String token, Account loginInfo, List<ComCodeDetailVo> comCodes) {
        this.result = result;
        this.msg = msg;
        this.token = token;
        this.loginInfo = loginInfo;
        this.comCodes = comCodes;
    }

    public static LoginResponse from(boolean result, String msg) {
        return LoginResponse.builder()
                           .result(result)
                           .msg(msg)
                           .build();
    }

    public static LoginResponse from(boolean result, String msg, String token, Account loginInfo, List<ComCodeDetailVo> comCodes) {
        return LoginResponse.builder()
                           .result(result)
                           .msg(msg)
                           .token(token)
                           .loginInfo(loginInfo)
                           .comCodes(comCodes)
                           .build();
    }

}
