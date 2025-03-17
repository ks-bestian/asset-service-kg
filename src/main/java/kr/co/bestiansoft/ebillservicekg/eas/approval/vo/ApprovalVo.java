package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApprovalVo  extends ComDefaultVO {
    private int approvalId;
    private String documentId;
    private String userId;
    private String userName;
    private String userDeptCd;
    private String userJobCd;
    private int approvalOrder;
    private LocalDateTime approvalDt;
    private LocalDateTime receivedDt;
    private LocalDateTime checkedDt;
    private String approvalFilePath;
    private String opinion;
    private String approvalStatus;
}
