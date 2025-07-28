package kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkRequestVo {
    private String docId;
    private int workReqId;
    private String workCn;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private char infoYn;
    private String workCycleCd;
    private String workStatus;
    private String regId;
    private LocalDateTime regDt;
    private int rcvId;
    private String regUserNm;

    @Builder
    public WorkRequestVo(String docId, int workReqId, String workCn, char tmlmtYn, LocalDateTime tmlmtDtm, char infoYn, String workCycleCd, String workStatus, String regId, LocalDateTime regDt, int rcvId, String regUserNm) {
        this.docId = docId;
        this.workReqId = workReqId;
        this.workCn = workCn;
        this.tmlmtYn = tmlmtYn;
        this.tmlmtDtm = tmlmtDtm;
        this.infoYn = infoYn;
        this.workCycleCd = workCycleCd;
        this.workStatus = workStatus;
        this.regId = regId;
        this.regDt = regDt;
        this.rcvId = rcvId;
        this.regUserNm = regUserNm;
    }
}