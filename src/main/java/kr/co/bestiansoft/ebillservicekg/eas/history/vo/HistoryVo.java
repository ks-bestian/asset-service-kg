package kr.co.bestiansoft.ebillservicekg.eas.history.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class HistoryVo {
    private int historyId;
    private String docId;
    private String userId;
    private String userNm;
    private String actType;
    private String actDetail;
    private LocalDateTime actDtm;

    @Builder
    public HistoryVo(int historyId, String docId, String userId, String userNm, String actType, String actDetail, LocalDateTime actDtm) {
        this.historyId = historyId;
        this.docId = docId;
        this.userId = userId;
        this.userNm = userNm;
        this.actType = actType;
        this.actDetail = actDetail;
        this.actDtm = actDtm;
    }
}