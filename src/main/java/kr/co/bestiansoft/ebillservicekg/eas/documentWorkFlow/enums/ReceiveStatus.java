package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum ReceiveStatus {
    BEFORE_SEND         ("RS00", "BEFORE_SEND"),
    SENT                ("RS01", "SENT"),
    VIEWED              ("RS02", "VIEWED"),
    REGISTERED          ("RS03", "REGISTERED"),
    REJECTED            ("RS04", "REJECTED"),
    COMPLETED_RESPONSE  ("RS05", "COMPLETED_RESPONSE"),
    APPROVING_RESPONSE  ("RS06", "APPROVING_RESPONSE");

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
