package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GatewayOrganizationResponseDto {
    @JsonProperty("orgNameRu")
    private String orgNameRu;

    @JsonProperty("orgInn")
    private String orgInn;

    @JsonProperty("orgNameKy")
    private String orgNameKy;

    private String created;
    private boolean enabled;
}