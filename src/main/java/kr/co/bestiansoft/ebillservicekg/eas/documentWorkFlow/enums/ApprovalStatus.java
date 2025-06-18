package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    PENDING     ("AS01", "대기"),
    SENT        ("AS02", "발송"),
    VIEWED      ("AS03", "열람"),
    APPROVED    ("AS04", "승인"),
    REJECTED    ("AS05", "반려");

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
