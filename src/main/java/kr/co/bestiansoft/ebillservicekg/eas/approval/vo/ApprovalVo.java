package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDateTime resDtm;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private String apvlHash;
    private String resOpinion;
    private String apvlStatusCd;
    private String apvlType;

    @Builder
    public ApprovalVo(int apvlId, String docId, String userId, String userNm, String deptCd, String jobCd, int apvlOrd, LocalDateTime resDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlHash, String resOpinion, String apvlStatusCd, String apvlType) {
        this.apvlId = apvlId;
        this.docId = docId;
        this.userId = userId;
        this.userNm = userNm;
        this.deptCd = deptCd;
        this.jobCd = jobCd;
        this.apvlOrd = apvlOrd;
        this.resDtm = resDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlHash = apvlHash;
        this.resOpinion = resOpinion;
        this.apvlStatusCd = apvlStatusCd;
        this.apvlType = apvlType;
    }
}