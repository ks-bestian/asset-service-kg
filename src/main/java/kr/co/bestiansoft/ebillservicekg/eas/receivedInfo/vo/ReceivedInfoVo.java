package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReceivedInfoVo extends ComDefaultVO {
    private int receivedId;
    private String documentId;
    private String userId;
    private String userName;
    private String userDeptCd;
    private String externalOrgCd;
    private String receiveStatus;
    private LocalDateTime receivedDt;
    private LocalDateTime checkedDt;
    private LocalDateTime acceptDt;
    private LocalDateTime rejectDt;
    private String rejectDetail;
    private int receivedOrder;
    private boolean isExternal;
}