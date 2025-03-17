package kr.co.bestiansoft.ebillservicekg.eas.history.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class HistoryVo extends ComDefaultVO {
    private int historyId;
    private String documentId;
    private String userId;
    private String userName;
    private String actionType;
    private String actionDetail;
    private LocalDateTime actionDt;
}