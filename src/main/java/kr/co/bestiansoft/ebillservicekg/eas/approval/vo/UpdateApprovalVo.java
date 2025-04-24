package kr.co.bestiansoft.ebillservicekg.eas.approval.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateApprovalVo {
    String apvl_id;
    LocalDateTime apvlDtm ;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    String apvlFileID;
    String apvlOpinion;
    String apvlStatusCd;

    @Builder
    public UpdateApprovalVo(String apvl_id, LocalDateTime apvlDtm, LocalDateTime rcvDtm, LocalDateTime checkDtm, String apvlFileID, String apvlOpinion, String apvlStatusCd) {
        this.apvl_id = apvl_id;
        this.apvlDtm = apvlDtm;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.apvlFileID = apvlFileID;
        this.apvlOpinion = apvlOpinion;
        this.apvlStatusCd = apvlStatusCd;
    }
}
