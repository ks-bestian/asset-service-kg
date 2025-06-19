package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums;

import lombok.Getter;

@Getter
public enum EasFileType {
    ATTACHMENT_FILE         ("EFT01", "첨부파일"),
    DRAFT_DOCUMENT_FILE     ("EFT02", "기안문서파일"),
    EXECUTION_REPLY_FILE    ("EFT03", "이행답변파일");

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
