package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String approvalDt;
    private String receivedDt;
    private String checkedDt;
    private String approvalFilePath;
    private String opinion;
    private String approvalStatus;
}
