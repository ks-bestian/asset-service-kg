package kr.co.bestiansoft.ebillservicekg.common.errLog.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ErrLogVo extends ComDefaultVO {

    private Long seq;
    private String acsIp;
    private String rqtUrl;
    private String rqtMthd;
    private Integer errCd;
    private String errMsg;

}
