package kr.co.bestiansoft.ebillservicekg.admin.menu.vo;


import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class QuickMenuVo extends ComDefaultVO {
    private Long seq;
    private String userId;
    private Long menuId;
    private Boolean isFavorite;
    private String menuNm;
    private String menuPath;
}
