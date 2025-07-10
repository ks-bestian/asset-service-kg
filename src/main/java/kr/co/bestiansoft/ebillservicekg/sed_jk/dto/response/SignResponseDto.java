package kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignResponseDto {
    private String message;
    private Boolean success;
}
