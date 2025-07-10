package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertInfoResponse {
    private String validNotBefore;
    private String validNotAfter;
    private String personIdnp;
    private String commonName;
    private String organizationInn;
    private String organizationName;
    private String countryName;
    private String keyIdentifier;
    private CertIssuer certissuer;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertIssuer {
        private String commonName;
        private String organizationName;
        private String countryName;
    }
}
