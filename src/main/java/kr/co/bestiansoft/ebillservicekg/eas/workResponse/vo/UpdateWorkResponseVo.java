package kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateWorkResponseVo {
    private int rspnsId;
    private String workReqId;
    private LocalDateTime checkDtm;
    private LocalDateTime rspnsDtm;
    private String rspnsCn;
    private String fileId;
    private String fileNm;
    private String userId;
    private int rcvId;

    @Builder
    public UpdateWorkResponseVo(int rspnsId, String workReqId, LocalDateTime checkDtm, LocalDateTime rspnsDtm, String rspnsCn, String fileId, String fileNm, String userId, int rcvId) {
        this.rspnsId = rspnsId;
        this.workReqId = workReqId;
        this.checkDtm = checkDtm;
        this.rspnsDtm = rspnsDtm;
        this.rspnsCn = rspnsCn;
        this.fileId = fileId;
        this.fileNm = fileNm;
        this.userId = userId;
        this.rcvId = rcvId;
    }
}
