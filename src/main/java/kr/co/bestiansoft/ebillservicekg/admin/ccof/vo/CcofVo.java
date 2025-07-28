package kr.co.bestiansoft.ebillservicekg.admin.ccof.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class CcofVo extends ComDefaultVO {
    private String userId;
    private String deptCd;
    private Integer ord;
    private String deptNm;
    private String shrtNm;
    private String uprDeptCd;

}
