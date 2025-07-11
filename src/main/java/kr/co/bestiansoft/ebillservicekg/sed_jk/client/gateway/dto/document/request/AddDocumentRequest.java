package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request;

import java.util.List;

public record AddDocumentRequest(
        String org_sender_inn,
        String org_receiver_inn,
        String doc_number,
        String doc_lang_ky,
        String doc_lang_ru,
        String doc_lang_fl,
        String doc_type, // e.g., "LETTER"
        String doc_registered, // "YYYY-MM-DD HH:MM:SS"
        String doc_deadline, // optional
        String doc_signature, // base64 encoded signature of the main document
        String signature_algorithm, // e.g., "gost3411"
        String doc_creater_name,
        String doc_signer_name,
        String doc_description,
        Boolean doc_fyi, // for your information
        String doc_uuid_related, // optional
        String doc_number_related, // optional
        String doc_registered_related, // optional
        AttachmentFileDto doc_file,
        List<AttachmentFileDto> doc_attach_files // optional
) {
}
