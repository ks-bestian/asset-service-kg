package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateReceivedInfoVo {
    private String docId;
    private int rcvId;
    private String rcvStatus;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private LocalDateTime rcpDtm;
    private LocalDateTime rjctDtm;
    private String rjctCn;
    private String workerId;
    private String hashFileId;
    private String rcpDocId;

    @Builder
    public UpdateReceivedInfoVo(String docId, int rcvId, String rcvStatus, LocalDateTime rcvDtm, LocalDateTime checkDtm, LocalDateTime rcpDtm, LocalDateTime rjctDtm, String rjctCn, String workerId, String hashFileId, String rcpDocId) {
        this.docId = docId;
        this.rcvId = rcvId;
        this.rcvStatus = rcvStatus;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.rcpDtm = rcpDtm;
        this.rjctDtm = rjctDtm;
        this.rjctCn = rjctCn;
        this.workerId = workerId;
        this.hashFileId = hashFileId;
        this.rcpDocId = rcpDocId;
    }
}
