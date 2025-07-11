package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response;

import java.util.List;

public record AddDocumentResponse(
        boolean success,
        String message,
        List<AddDocumentResponseResult> result,
        String time,
        String ver
) {
}
