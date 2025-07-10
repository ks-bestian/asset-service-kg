package kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer errorCode;
    private String errorMessage;
    // Поля могут меняться в зависимости от типа ошибки
    private Boolean isActive;
    private String inactiveReason;
}
