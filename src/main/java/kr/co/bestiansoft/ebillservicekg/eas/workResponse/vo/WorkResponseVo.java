package kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.*;

import java.time.LocalDateTime;

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
    private String rspnsCn;
    private String fileId;
    private String fileNm;
}