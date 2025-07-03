package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum WorkStatus {
    DISPATCHED          ("WS01", "DISPATCHED"),
    PENDING_EXECUTION   ("WS02", "PENDING_EXECUTION"),
    EXECUTION_COMPLETE  ("WS03", "EXECUTION_COMPLETE");

    private final String codeId;
    private final String codeNm;

    WorkStatus(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static WorkStatus fromCode(String codeId) {
        for (WorkStatus workStatus : WorkStatus.values()) {
            if (workStatus.getCodeId().equals(codeId)) {
                return workStatus;
            }
        }
        return null;
    }
}
