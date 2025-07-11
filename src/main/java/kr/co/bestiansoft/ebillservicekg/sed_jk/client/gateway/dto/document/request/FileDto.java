package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request;

public record FileDto(
        String fileName,
        String fileExt,
        String fileBody,
        String fileSignature
) {
}
