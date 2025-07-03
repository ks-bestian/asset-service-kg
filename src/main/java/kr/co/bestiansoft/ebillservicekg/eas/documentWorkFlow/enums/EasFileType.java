package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum EasFileType {
    ATTACHMENT_FILE         ("EFT01", "ATTACHMENT_FILE"),
    DRAFT_DOCUMENT_FILE     ("EFT02", "DRAFT_DOCUMENT_FILE"),
    EXECUTION_REPLY_FILE    ("EFT03", "EXECUTION_REPLY_FILE");

    private final String codeId;
    private final String codeNm;

    EasFileType(String codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }

    public EasFileType fromCode(String codeId) {
        for(EasFileType easFileType : EasFileType.values()) {
            if(easFileType.getCodeId().equals(codeId)){
                return easFileType;
            }
        }
        return null;
    }
}
