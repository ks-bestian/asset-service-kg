package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum LinkType {
    LINK_DOCUMENT("LT01", "LINK_DOCUMENT"),
    LINK_REPLY("LT02", "LINK_REPLY");

    private final String codeId;
    private final String codeNm;

    LinkType(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }
    public static LinkType fromCode(String codeId) {
        for (LinkType linkType : LinkType.values()) {
            if (linkType.getCodeId().equals(codeId)) {
                return linkType;
            }
        }
        return null;
    }
}
