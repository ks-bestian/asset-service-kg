package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ReceiveStatus {
    BEFORE_SEND         ("RS00", "발송 이전"),
    SENT                ("RS01", "발송"),
    VIEWED              ("RS02", "열람"),
    REGISTERED          ("RS03", "등록"),
    REJECTED            ("RS04", "반려"),
    COMPLETED_RESPONSE  ("RS05", "답변완료"),
    APPROVING_RESPONSE  ("RS06", "답변 승인 중");

    // todo 답변 승인 중
    private final String codeId;
    private final String codeNm;

    ReceiveStatus(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static ReceiveStatus fromCode(String codeId) {
        for (ReceiveStatus receiveStatus : ReceiveStatus.values()) {
            if (receiveStatus.getCodeId().equals(codeId)) {
                return receiveStatus;
            }
        }
        return null;
    }
}
