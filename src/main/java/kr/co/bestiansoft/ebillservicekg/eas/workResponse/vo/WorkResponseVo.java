package kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WorkResponseVo extends ComDefaultVO {
    private int responseId;
    private String workRequestId;
    private String userId;
    private String userDeptCd;
    private String userJobCd;
    private LocalDateTime checkedDt;
    private LocalDateTime postDt;
    private String responseContent;
    private String postFileId;
}