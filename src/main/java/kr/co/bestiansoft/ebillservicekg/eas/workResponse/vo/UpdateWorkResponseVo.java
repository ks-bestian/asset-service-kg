package kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateWorkResponseVo {
    int rspnsId;
    LocalDateTime checkDtm;
    LocalDateTime rspnsDtm;
    String rspnsCn;
    String fileId;
}
