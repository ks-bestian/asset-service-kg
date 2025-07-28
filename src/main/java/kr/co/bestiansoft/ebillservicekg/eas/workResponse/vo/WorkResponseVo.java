package kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkResponseVo {
    private int rspnsId;
    private int workReqId;
    private String userId;
    private String deptCd;
    private String jobCd;
    private String userNm;
    private LocalDateTime checkDtm;
    private LocalDateTime rspnsDtm;
    private LocalDateTime rcvDtm;
    private String rspnsCn;
    private String fileId;
    private String fileNm;
}