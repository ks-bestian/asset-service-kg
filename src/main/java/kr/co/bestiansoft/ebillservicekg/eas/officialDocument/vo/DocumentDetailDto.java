package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class DocumentDetailDto {
    String docId;
    String aarsFileId;
    String aarsPdfFileId;
    String docTypeCd;
    String docAttrbCd;
    String billId;
    String billNo;
    String tmlmtYn;
    LocalDateTime tmlmtDtm;
    String docLng;
    String docSubtle;
    String digitalYn;
    String senderId;
    String senderNm;
    String deptCd;
    String docNo;
    LocalDateTime regDtm;

    List<EasFileVo> files;
}
