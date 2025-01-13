package kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AcsHistVo extends ComDefaultVO {

    private Long seq;
    private String acsIp;
    private String reqUrl;
    private String reqMethod;
    private String regId;
    private LocalDateTime regDt;
    private String memberNm;


}
