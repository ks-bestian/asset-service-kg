package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
