package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class OfficialDocumentVo extends ComDefaultVO {
    private String documentId ;
    private int draftId ;
    private String writerId ;
    private String documentType ;
    private String documentAttribute ;
    private String billId ;
    private boolean hasDeadline ;
    private LocalDateTime deadlineDt ;
    private String documentLanguage ;
    private String documentTheme ;
    private String documentStatus ;
    private boolean digitalYn ;
    private String senderId ;
    private String senderName ;
    private String senderDeptCd ;
    private String documentNum ;
}
