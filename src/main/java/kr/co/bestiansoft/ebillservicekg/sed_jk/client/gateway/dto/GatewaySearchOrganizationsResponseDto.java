package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import java.util.List;

import lombok.Data;

@Data
public class GatewaySearchOrganizationsResponseDto extends GatewayResponseDto {
    public List<GatewayOrganizationResponseDto> result;
}
