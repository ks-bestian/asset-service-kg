package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ApprovalType {
    REQUEST_APPROVAL            ("AVT01", "REQUEST_APPROVAL"),
    REQUEST_REPLY_APPROVAL      ("AVT02", "REQUEST_REPLY_APPROVAL"),
    REQUEST_REVIEW_AND_APPROVAL ("AVT03", "REQUEST_REVIEW_AND_APPROVAL");

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
