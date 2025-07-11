package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request;

public record AttachmentFileDto(
        String fileName,
        String fileExt,
        String fileBody, // base64 encoded content
        String fileSignature
) {
}
