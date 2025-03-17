package kr.co.bestiansoft.ebillservicekg.eas.file.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class EasFileVo extends ComDefaultVO {
    private String fileId;
    private String documentId;
    private String filetype;
    private String orgFileId;
    private String orgFileName;
    private String pdfFileId;
    private String pdfFileName;
    private int fileSize;
    private String fileExt;
    private boolean deleteYn;
    private LocalDateTime deleteDt;
    private String deleteUserId;
    private String regId;
    private LocalDateTime regDt;

}
