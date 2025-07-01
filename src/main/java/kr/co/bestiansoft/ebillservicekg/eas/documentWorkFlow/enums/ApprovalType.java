package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ApprovalType {
    REQUEST_APPROVAL            ("AVT01", "결재요청"),
    REQUEST_REPLY_APPROVAL      ("AVT02", "답변 승인 요청"),
    REQUEST_REVIEW_AND_APPROVAL ("AVT03", "옵시아이딜 승인 요청");

    private final String codeId;
    private final String codeNm;

    ApprovalType(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static ApprovalType fromCode(String codeId) {
        for (ApprovalType approvalType : ApprovalType.values()) {
            if (approvalType.getCodeId().equals(codeId)) {
                return approvalType;
            }
        }
        return null;
    }
}
