package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto;

import lombok.Data;

@Data
public class GatewayResponseDto {
    private boolean success;
    private String message;
    private String time;
    private String ver;
}
