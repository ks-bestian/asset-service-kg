package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateApprovalVo {
    private String docId;
    private int apvlId;
    private LocalDateTime resDtm ;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private String apvlHash;
    private String resOpinion;
    private String apvlStatusCd;
    private String apvlType;

    @Builder
    public UpdateApprovalVo(String docId, int apvlId, LocalDateTime resDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlHash, String resOpinion, String apvlStatusCd, String apvlType) {
        this.docId = docId;
        this.apvlId = apvlId;
        this.resDtm = resDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlHash = apvlHash;
        this.resOpinion = resOpinion;
        this.apvlStatusCd = apvlStatusCd;
        this.apvlType = apvlType;
    }
}
