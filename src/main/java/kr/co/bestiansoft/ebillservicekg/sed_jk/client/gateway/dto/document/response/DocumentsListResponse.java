package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewayResponseDto;
import lombok.Data;

@Data
public class DocumentsListResponse extends GatewayResponseDto {
    public DocumentsListResult result;
}
