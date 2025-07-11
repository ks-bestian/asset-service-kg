package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response;

public record AddDocumentResponseResult(
        String docNumber,
        String docUuid,
        String fileName,
        String fileLink
) {
}
