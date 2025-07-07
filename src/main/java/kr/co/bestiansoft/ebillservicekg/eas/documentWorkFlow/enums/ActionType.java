package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;


@Getter
public enum ActionType {
    // Enum Constant (English uppercase), code (ATXx)
    CREATE_DOCUMENT         ("AT01", "CREATE_DOCUMENT"),
    APPROVE_SIGN            ("AT02", "APPROVE_SIGN"),
    REGISTER_DOCUMENT       ("AT03", "REGISTER_DOCUMENT"),
    REJECT_DOCUMENT         ("AT04", "REJECT_DOCUMENT"),
    DELETE_EXECUTOR         ("AT05", "DELETE_EXECUTOR"),
    ADD_EXECUTOR_DETAILS    ("AT06", "ADD_EXECUTOR_DETAILS"),
    APPROVE_REPLY           ("AT07", "APPROVE_REPLY"),
    WRITE_REPLY             ("AT08", "WRITE_REPLY"),
    LINK_DOCUMENT           ("AT09", "LINK_DOCUMENT"),
    REJECT_APPROVAL         ("AT10", "REJECT_APPROVAL"),
    SIGN_DOCUMENT           ("AT11", "SIGN_DOCUMENT"),
    COMPLETE_DOCUMENT       ("AT12", "COMPLETE_DOCUMENT"),
    UPDATE_REQUEST          ("AT13", "UPDATE_REQUEST"),
    UPDATE_MAIN_RESPONSER   ("AT14", "UPDATE_MAIN_RESPONSER"),
    INSERT_EXECUTOR         ("AT15", "INSERT_EXECUTOR");


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
