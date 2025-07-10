package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinCodeRequest {
    private String personIdnp;
    private String organizationInn;
    private String method;
}
