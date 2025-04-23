package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateReceivedInfoVo {
    String rcvStatus;
    LocalDateTime rcvDtm;
    LocalDateTime checkDtm;
    LocalDateTime rcpDtm;
    LocalDateTime rjctDtm;
    String rjctCn;
}
