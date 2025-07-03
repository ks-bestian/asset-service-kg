package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    PENDING     ("AS01", "PENDING"),
    SENT        ("AS02", "SENT"),
    VIEWED      ("AS03", "VIEWED"),
    APPROVED    ("AS04", "APPROVED"),
    REJECTED    ("AS05", "REJECTED");

    final private String codeId;
    final private String codeNm;


    ApprovalStatus(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static ApprovalStatus fromCode(String codeId) {
        for (ApprovalStatus approvalStatus : ApprovalStatus.values()) {
            if (approvalStatus.getCodeId().equals(codeId)) {
                return approvalStatus;
            }
        }
        return null;
    }
}
