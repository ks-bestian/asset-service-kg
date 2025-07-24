package kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;


@Data
public class BzentyVo extends ComDefaultVO {

    private String bzentyId;
    private String bzentyNm1;
    private String bzentyNm2;
    private String bzentyNm3;
    private String telno;
    private String eml;
    private String picNm;
    private String useYn;

}
