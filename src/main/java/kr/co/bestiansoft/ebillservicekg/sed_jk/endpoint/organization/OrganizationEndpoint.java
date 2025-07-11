package kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewayAllOrganizationsResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewayOrganizationResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.dto.OrganizationAddDto;

import java.util.List;

public interface OrganizationEndpoint {
    List<GatewayOrganizationResponseDto> search(String name);
    List<GatewayOrganizationResponseDto> myList(String page, String size);
    List<GatewayOrganizationResponseDto> otherList();

    List<GatewayAllOrganizationsResponseDto.SedSystemsResponseDto.SedSystemResponseDto> listAll();

    OrganizationAddDto enable(String inn);

    OrganizationAddDto disable(String inn);
    OrganizationAddDto edit(String inn, OrganizationAddDto dto);
}
