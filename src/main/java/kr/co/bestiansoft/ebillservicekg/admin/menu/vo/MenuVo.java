package kr.co.bestiansoft.ebillservicekg.admin.menu.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MenuVo extends ComDefaultVO {
    private Long menuId;
    private String menuNm;
    private String menuNm1;
    private String menuNm2;
    private String menuNm3;
    private Long ord;
    private Long uprMenuId;
    private String menuPath;
    private String useYn;
    private String rmk;
}
