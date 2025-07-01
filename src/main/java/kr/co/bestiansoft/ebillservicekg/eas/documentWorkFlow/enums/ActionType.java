package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;


@Getter
public enum ActionType {
    // Enum 상수 (영문 대문자), 코드 (ATxx), 한국어 설명
    CREATE_DOCUMENT     ("AT01", "문서작성"),
    APPROVE_SIGN        ("AT02", "결재서명"),
    REGISTER_DOCUMENT   ("AT03", "문서 등록"),
    REJECT_DOCUMENT     ("AT04", "문서 반려"),
    ADD_EXECUTOR        ("AT05", "이행자 추가"),
    ADD_EXECUTOR_DETAILS("AT06", "이행자 내용 추가"),
    APPROVE_REPLY       ("AT07", "답변 승인"),
    WRITE_REPLY         ("AT08", "답변 작성"),
    LINK_DOCUMENT       ("AT09", "문서 연결"),
    REJECT_APPROVAL     ("AT10", "결재 반려"),
    SIGN_DOCUMENT       ("AT11", "문서 서명"),
    COMPLETE_DOCUMENT   ("AT12", "문서 종료");


    private final String codeId;
    private final String codeNm;

    ActionType(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public static ActionType fromCode(String codeId) {
        for (ActionType actionType : ActionType.values()) {
            if (actionType.getCodeId().equals(codeId)) {
                return actionType;
            }
        }
        return null;
    }

}
