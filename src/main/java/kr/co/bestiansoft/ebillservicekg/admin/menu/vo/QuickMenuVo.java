package kr.co.bestiansoft.ebillservicekg.admin.menu.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuickMenuVo extends ComDefaultVO {
    private Long seq;
    private String userId;
    private Long menuId;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
