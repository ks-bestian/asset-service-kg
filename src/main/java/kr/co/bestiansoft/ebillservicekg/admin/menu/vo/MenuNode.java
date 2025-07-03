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
    int level;
    int menuAuth;
    boolean menuChecked;

    String lngId;
    String lngTy1;
    String lngTy2;
    String lngTy3;

    private List<MenuNode> children = new ArrayList<>();


    public void addChildMenu(MenuNode child) {
        this.children.add(child);
    }

    @Builder
    public MenuNode(Long menuId, String menuNm, Long uprMenuId, String menuPath) {
        this.menuId = menuId;
        this.menuNm = menuNm;
        this.uprMenuId = uprMenuId;
        this.menuPath = menuPath; // addition(20250203 Jinho Cho)
    }

}
