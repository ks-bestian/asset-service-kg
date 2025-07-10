package kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response.CheckSignResponse;

public record CheckResponseDto(
        Boolean success,
        String message,
        CheckSignResponse data
) {
}
