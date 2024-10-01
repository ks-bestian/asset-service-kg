package kr.co.bestiansoft.ebillservicekg.admin.menu.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuInitResponse {
    private Long menuId;
    private Integer ord;
    private String menuNm;
    private String menuNm1;
    private String menuNm2;
    private String menuNm3;
    private String menuNm4;
    private String path;
    private Long uprMenuId;
    private LocalDateTime regDt;
    private String regId;
    private LocalDateTime modDt;
    private String modId;
    private String rmk;
}
