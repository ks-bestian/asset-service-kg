package kr.co.bestiansoft.ebillservicekg.admin.menu.vo;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MenuNode {
    Long menuId;
    String menuNm;
    String menuNm1;
    String menuNm2;
    String menuNm3;
    Long ord;
    Long uprMenuId;
    String menuPath;
    String useYn;
    String rmk;
    LocalDateTime regDt;
    String regId;
    LocalDateTime modDt;
    String modId;

    int level;
    int menuAuth;

    String lngId;
    String lngTy1;
    String lngTy2;
    String lngTy3;

    private List<MenuNode> children = new ArrayList<>();

    @Builder
    public MenuNode(Long menuId, Long ord, String menuNm, String menuNm1, String menuNm2, String menuNm3, String menuPath, Long uprMenuId, LocalDateTime regDt, String regId, LocalDateTime modDt, String modId, String rmk, String useYn, int menuAuth) {
        this.menuId = menuId;
        this.ord = ord;
        this.menuNm = menuNm;
        this.menuNm1 = menuNm1;
        this.menuNm2 = menuNm2;
        this.menuNm3 = menuNm3;
        this.menuPath = menuPath;
        this.uprMenuId = uprMenuId;
        this.regDt = regDt;
        this.regId = regId;
        this.modDt = modDt;
        this.modId = modId;
        this.rmk = rmk;
        this.useYn = useYn;
        this.menuAuth = menuAuth;
    }


    public void addChildMenu(MenuNode child) {
        this.children.add(child);
    }

}
