package kr.co.bestiansoft.ebillservicekg.admin.menu.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuVo {
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
