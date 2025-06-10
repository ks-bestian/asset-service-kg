package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateReceivedInfoVo {

    int rcvId;
    String rcvStatus;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    LocalDateTime rcpDtm;
    LocalDateTime rjctDtm;
    String rjctCn;
    String workerId;


    @Builder
    public UpdateReceivedInfoVo(int rcvId, String rcvStatus, LocalDateTime rcvDtm, LocalDateTime checkDtm, LocalDateTime rcpDtm, LocalDateTime rjctDtm, String rjctCn, String workerId) {
        this.rcvId = rcvId;
        this.rcvStatus = rcvStatus;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.rcpDtm = rcpDtm;
        this.rjctDtm = rjctDtm;
        this.rjctCn = rjctCn;
        this.workerId = workerId;
    }
}
