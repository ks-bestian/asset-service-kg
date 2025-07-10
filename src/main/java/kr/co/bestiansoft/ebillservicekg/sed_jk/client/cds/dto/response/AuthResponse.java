package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String createAt;
    private String expireAt;
    private Integer subjectType;
    private String personIdnp;
    private String personFio;
    private String organizationInn;
    private String organizationName;
    private Boolean isActive;
    private String token;
}
