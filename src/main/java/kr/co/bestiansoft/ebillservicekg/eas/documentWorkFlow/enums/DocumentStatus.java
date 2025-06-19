package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum DocumentStatus {
    // Enum 상수 (영문 대문자), 코드 (DMSxx), 한국어 설명
    APPROVING   ("DMS01", "승인중"),
    TRANSMITTED ("DMS02", "전송"),
    REGISTERED  ("DMS03", "등록"),
    REJECTED    ("DMS04", "반려"),
    COMPLETED   ("DMS05", "종료");

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
