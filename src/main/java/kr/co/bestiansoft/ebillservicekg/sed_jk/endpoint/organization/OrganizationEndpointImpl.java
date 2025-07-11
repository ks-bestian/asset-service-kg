package kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.GatewayApi;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.GatewayClient;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.*;
import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.dto.OrganizationAddDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationEndpointImpl implements OrganizationEndpoint {
    private final GatewayClient gatewayClient;

    @Override
    public List<GatewayOrganizationResponseDto> search(String name) {
        Map<String, String> filters = new HashMap<>();

        filters.put("name", name);

        Consumer<HttpHeaders> httpHeaders = headers -> {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        };

        GatewaySearchOrganizationsResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.SEARCH_ORGANIZATION,
                null, GatewaySearchOrganizationsResponseDto.class, filters, httpHeaders);

        if (!apiResponse.isSuccess()) {
            log.error("Sed API list organizations returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult();
    }

    @Override
    public List<GatewayOrganizationResponseDto> myList(String page, String size) {
        Map<String, String> filters = new HashMap<>();

        GatewayOrganizationsDto apiResponse = gatewayClient.sendRequest(GatewayApi.LIST_MY_ORGANIZATIONS,null, GatewayOrganizationsDto.class, filters, List.of(page, size));

        if (!apiResponse.isSuccess()) {
            log.error("Sed API list organizations returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult().getContent();
    }

    @Override
    public List<GatewayAllOrganizationsResponseDto.SedSystemsResponseDto.SedSystemResponseDto> listAll() {
        Map<String, String> filters = new HashMap<>();

        GatewayAllOrganizationsResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.LIST_ALL_ORGANIZATIONS,null, GatewayAllOrganizationsResponseDto.class, filters);

        if (!apiResponse.isSuccess()) {
            log.error("Sed API list all organizations returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult().getSystems();
    }

    @Override
    public List<GatewayOrganizationResponseDto> otherList() {
        Map<String, String> filters = new HashMap<>();

        GatewayAllOrganizationsResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.LIST_OTHER_ORGANIZATIONS,null, GatewayAllOrganizationsResponseDto.class, filters);

        if (!apiResponse.isSuccess()) {
            log.error("Sed API list all organizations returned error: {}", apiResponse.getMessage());
        }

        List<GatewayOrganizationResponseDto> organizations = new ArrayList<>();

        for (GatewayAllOrganizationsResponseDto.SedSystemsResponseDto.SedSystemResponseDto systemsResponseDtos:  apiResponse.getResult().getSystems()){

            for (GatewayOrganizationResponseDto organization: systemsResponseDtos.getOrganizations()){
                if(organization.isEnabled())
                    organizations.add(organization);
            }
        }

        return organizations;
    }

    @Override
    public OrganizationAddDto enable(String inn) {
        Map<String, String> filters = new HashMap<>();

        GatewayAddOrganizationResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.ENABLE_ORGANIZATION, null, GatewayAddOrganizationResponseDto.class, filters, List.of(inn));

        if (!apiResponse.isSuccess()) {
            log.error("Sed API enable organization returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult();
    }


    @Override
    public OrganizationAddDto disable(String inn) {
        Map<String, String> filters = new HashMap<>();

        GatewayAddOrganizationResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.DISABLE_ORGANIZATION, null, GatewayAddOrganizationResponseDto.class, filters, List.of(inn));

        if (!apiResponse.isSuccess()) {
            log.error("Sed API disable organization returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult();
    }

    @Override
    public OrganizationAddDto edit(String inn, OrganizationAddDto dto) {
        Map<String, String> filters = new HashMap<>();

        GatewayAddOrganizationResponseDto apiResponse = gatewayClient.sendRequest(GatewayApi.EDIT_ORGANIZATION, dto, GatewayAddOrganizationResponseDto.class, filters, List.of(inn));

        if (!apiResponse.isSuccess()) {
            log.error("Sed API add organization returned error: {}", apiResponse.getMessage());
        }

        return apiResponse.getResult();
    }
}
