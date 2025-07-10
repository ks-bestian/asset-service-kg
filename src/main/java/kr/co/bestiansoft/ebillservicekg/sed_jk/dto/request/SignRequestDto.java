package kr.co.bestiansoft.ebillservicekg.sed_jk.dto.request;

import jakarta.validation.constraints.NotNull;

public record SignRequestDto(
        int apvlId,
        String pinCode
) {
}
