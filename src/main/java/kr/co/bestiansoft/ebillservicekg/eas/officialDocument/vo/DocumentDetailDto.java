package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class DocumentDetailDto {
    private String docId;
    private String aarsFileId;
    private String aarsPdfFileId;
    private String docTypeCd;
    private String docAttrbCd;
    private String billId;
    private String billNo;
    private String tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private String docLng;
    private String docSubtle;
    private String digitalYn;
    private String senderId;
    private String senderNm;
    private String senderDeptCd;
    private String docNo;
    private LocalDateTime regDtm;
    private String externalYn;

    private List<EasFileVo> files;
}
