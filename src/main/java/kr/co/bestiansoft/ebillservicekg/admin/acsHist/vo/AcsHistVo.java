package kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AcsHistVo extends ComDefaultVO {

    private Long seq;
    private String acsIp;
    private String reqUrl;
    private String reqMethod;
    private String memberNm;


}
