package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
public class DraftDocumentVo extends ComDefaultVO {
    private int aarsDocId;
    private int formId;
    private String aarsFileId;
    private String aarsStatusCd;
    private String aarsPdfFileId;
}