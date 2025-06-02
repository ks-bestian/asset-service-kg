package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateApprovalVo {
    String docId;
    int apvlId;
    LocalDateTime apvlDtm ;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    String apvlFileId;
    String apvlOpinion;
    String apvlStatusCd;

    @Builder
    public UpdateApprovalVo(String docId, int apvlId, LocalDateTime apvlDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlFileId, String apvlOpinion, String apvlStatusCd) {
        this.docId = docId;
        this.apvlId = apvlId;
        this.apvlDtm = apvlDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlFileId = apvlFileId;
        this.apvlOpinion = apvlOpinion;
        this.apvlStatusCd = apvlStatusCd;
    }
}
