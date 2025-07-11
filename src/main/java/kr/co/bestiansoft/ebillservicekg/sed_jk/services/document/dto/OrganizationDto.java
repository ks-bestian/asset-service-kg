package kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.dto;

import jakarta.validation.constraints.NotNull;

public record OrganizationDto(
        @NotNull String orgNameKy,
        @NotNull String orgNameRu,
        @NotNull String orgInn
) {
}
