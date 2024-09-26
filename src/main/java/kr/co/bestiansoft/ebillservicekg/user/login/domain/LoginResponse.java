package kr.co.bestiansoft.ebillservicekg.user.login.domain;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private Account account;

    
}
