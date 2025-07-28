package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMethodResponse {
    private List<UserAuthMethod> userAuthMethodList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAuthMethod {
        private String authType;
        private Boolean isActive;
    }
}
