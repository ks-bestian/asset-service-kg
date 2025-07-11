package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.dto.OrganizationAddDto;
import lombok.Data;

@Data
public class GatewayAddOrganizationResponseDto extends GatewayResponseDto {
    private OrganizationAddDto result;
}
