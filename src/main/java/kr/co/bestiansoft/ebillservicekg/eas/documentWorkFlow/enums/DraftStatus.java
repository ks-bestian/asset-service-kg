package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum DraftStatus {
    TEMPORARY_SAVE  ("DS01", "TEMPORARY_SAVE"),
    DISPATCHED      ("DS02", "DISPATCHED");

    private final String codeId;
    private final String codeNm;

    DraftStatus(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static DraftStatus fromCode(String codeId) {
        for (DraftStatus draftStatus : DraftStatus.values()) {
            if (draftStatus.getCodeId().equals(codeId)) {
                return draftStatus;
            }
        }
        return null;
    }
}
