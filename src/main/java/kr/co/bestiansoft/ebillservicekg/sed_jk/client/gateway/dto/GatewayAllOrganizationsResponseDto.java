package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GatewayAllOrganizationsResponseDto extends GatewayResponseDto {
    private SedSystemsResponseDto result;

    @Data
    public static class SedSystemsResponseDto {
        public List<SedSystemResponseDto> systems;

        @Data
        public static class SedSystemResponseDto {
            @JsonProperty("edmsAlias")
            private String edmsAlias;

            @JsonProperty("edmsName")
            private String edmsName;

            private List<GatewayOrganizationResponseDto> organizations;
        }
    }
}
