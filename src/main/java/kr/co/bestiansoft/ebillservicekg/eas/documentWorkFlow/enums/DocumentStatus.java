package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum DocumentStatus {
    // EnumConstant (English uppercase), code (DMSXX)
    APPROVING   ("DMS01", "APPROVING"),
    TRANSMITTED ("DMS02", "TRANSMITTED"),
    REGISTERED  ("DMS03", "REGISTERED"),
    REJECTED    ("DMS04", "REJECTED"),
    COMPLETED   ("DMS05", "COMPLETED");

    private final String codeId;
    private final String codeNm;

    DocumentStatus(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static DocumentStatus fromCode(String codeId) {
        for (DocumentStatus documentStatus : DocumentStatus.values()) {
            if (documentStatus.getCodeId().equals(codeId)) {
                return documentStatus;
            }
        }
        return null;
    }
}
