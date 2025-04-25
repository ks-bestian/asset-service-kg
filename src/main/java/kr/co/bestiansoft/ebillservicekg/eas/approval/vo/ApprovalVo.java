package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApprovalVo  {
    private int apvlId;
    private String docId;
    private String userId;
    private String userNm;
    private String deptCd;
    private String jobCd;
    private int apvlOrd;
    private LocalDateTime apvlDtm;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private String apvlFileId;
    private String apvlOpinion;
    private String apvlStatusCd;

    @Builder
    public ApprovalVo(int apvlId, String docId, String userId, String userNm, String deptCd, String jobCd, int apvlOrd, LocalDateTime apvlDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlFileId, String apvlOpinion, String apvlStatusCd) {
        this.apvlId = apvlId;
        this.docId = docId;
        this.userId = userId;
        this.userNm = userNm;
        this.deptCd = deptCd;
        this.jobCd = jobCd;
        this.apvlOrd = apvlOrd;
        this.apvlDtm = apvlDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlFileId = apvlFileId;
        this.apvlOpinion = apvlOpinion;
        this.apvlStatusCd = apvlStatusCd;
    }
}