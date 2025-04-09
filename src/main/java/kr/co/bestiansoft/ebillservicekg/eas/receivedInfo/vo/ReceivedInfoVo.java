package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReceivedInfoVo extends ComDefaultVO {
    private int rcvId;
    private String docId;
    private String userId;
    private String userNm;
    private String deptCd;
    private String extOrgnCd;
    private String rcvStatus;
    private LocalDateTime rcvDtm;
    private LocalDateTime checkDtm;
    private LocalDateTime rcpDtm;
    private LocalDateTime rjctDtm;
    private String rjctCn;
    private int rcvOrd;

    @Builder
    public ReceivedInfoVo(int rcvId, String docId, String userId, String userNm, String deptCd, String extOrgnCd, String rcvStatus, LocalDateTime rcvDtm, LocalDateTime checkDtm, LocalDateTime rcpDtm, LocalDateTime rjctDtm, String rjctCn, int rcvOrd) {
        this.rcvId = rcvId;
        this.docId = docId;
        this.userId = userId;
        this.userNm = userNm;
        this.deptCd = deptCd;
        this.extOrgnCd = extOrgnCd;
        this.rcvStatus = rcvStatus;
        this.rcvDtm = rcvDtm;
        this.checkDtm = checkDtm;
        this.rcpDtm = rcpDtm;
        this.rjctDtm = rjctDtm;
        this.rjctCn = rjctCn;
        this.rcvOrd = rcvOrd;
    }
}