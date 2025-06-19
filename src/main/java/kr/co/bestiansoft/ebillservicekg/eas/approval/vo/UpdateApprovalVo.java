package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateApprovalVo {
    private String docId;
    private int apvlId;
    private LocalDateTime resDtm ;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private String apvlFileId;
    private String resOpinion;
    private String apvlStatusCd;

    @Builder
    public UpdateApprovalVo(String docId, int apvlId, LocalDateTime resDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlFileId, String resOpinion, String apvlStatusCd) {
        this.docId = docId;
        this.apvlId = apvlId;
        this.resDtm = resDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlFileId = apvlFileId;
        this.resOpinion = resOpinion;
        this.apvlStatusCd = apvlStatusCd;
    }
}
