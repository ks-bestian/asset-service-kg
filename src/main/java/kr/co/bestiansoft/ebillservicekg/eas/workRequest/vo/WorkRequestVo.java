package kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WorkRequestVo extends ComDefaultVO {

    private int workRequestId;
    private String workDetail;
    private boolean hasDeadline;
    private LocalDateTime workDeadlineDt;
    private boolean isInformation;
    private String workCycleCd;
    private String workStatus;

}