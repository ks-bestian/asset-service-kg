package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import lombok.Data;

import java.util.List;

@Data
public class GatewayOrganizationsDto extends GatewayResponseDto {
    private SedOrganizationsPageDto result;

    @Data
    public class SedOrganizationsPageDto {
        private int totalItems;
        private int totalPages;
        private int currentPage;
        private List<GatewayOrganizationResponseDto> content;
    }
}
