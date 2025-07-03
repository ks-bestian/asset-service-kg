package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum DocumentType {
    REPLY_PURPOSE       ("DMT01", "REPLY_PURPOSE"),
    CONFIRMATION_PURPOSE("DMT02", "CONFIRMATION_PURPOSE"),
    SIGNATURE_PURPOSE   ("DMT03", "SIGNATURE_PURPOSE");

    private final String codeId;
    private final String codeNm;

    DocumentType(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static DocumentType fromCode(String codeId) {
        for (DocumentType documentType : DocumentType.values()) {
            if (documentType.getCodeId().equals(codeId)) {
                return documentType;
            }
        }
        return null;
    }
}
