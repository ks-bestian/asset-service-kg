package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class DraftDocumentVo extends ComDefaultVO {
    private int draftId;
    private String draftRecipientsName;
    private String draftTitle;
    private String draftDetail;
    private String draftFileName;
    private String senderName;
    private String senderDeptName;
    private String senderJobName;
    private String filePath;
    private String draftStatus;
}
