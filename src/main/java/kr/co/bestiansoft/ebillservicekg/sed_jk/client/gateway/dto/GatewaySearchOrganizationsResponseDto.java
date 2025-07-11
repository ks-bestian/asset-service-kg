package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
public class GatewaySearchOrganizationsResponseDto extends GatewayResponseDto {
    public List<GatewayOrganizationResponseDto> result;
}
