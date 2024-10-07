package kr.co.bestiansoft.ebillservicekg.admin.menu.domain;

import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.entity.MenuEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MenuInitResponse {
    private Long menuId;
    private Integer ord;
    private String menuNm;
    private String path;
    private Long uprMenuId;
    private LocalDateTime regDt;
    private String regId;
    private LocalDateTime modDt;
    private String modId;
    private String rmk;

    public static MenuInitResponse from(MenuEntity menu, String languageType) {
        return MenuInitResponse.builder()
                               .menuId(menu.getMenuId())
                               .ord(menu.getOrd())
                               .menuNm(languageType.equals("lng_type_1") ? menu.getMenuNm1() : menu.getMenuNm2())
                               .path(menu.getPath())
                               .uprMenuId(menu.getUprMenuId())
                               .regDt(menu.getRegDt())
                               .regId(menu.getRegId())
                               .modDt(menu.getModDt())
                               .modId(menu.getModId())
                               .rmk(menu.getRmk())
                               .build();
    }
}
