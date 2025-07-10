package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSignResponse {
    private String personIdnp;
    private String personFio;
    private String organizationInn;
    private String organizationName;
    private Boolean verified;
    private Long timestamp;
}
