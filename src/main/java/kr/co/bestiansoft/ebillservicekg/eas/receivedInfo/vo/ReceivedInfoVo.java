package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReceivedInfoVo extends ComDefaultVO {
    private int received_id;
    private String document_id;
    private String user_id;
    private String user_name;
    private String user_dept_cd;
    private String external_org_cd;
    private String receive_status;
    private LocalDateTime received_dt;
    private LocalDateTime checked_dt;
    private LocalDateTime accept_dt;
    private LocalDateTime reject_dt;
    private String reject_detail;
    private int received_order;
    private boolean is_external;
}