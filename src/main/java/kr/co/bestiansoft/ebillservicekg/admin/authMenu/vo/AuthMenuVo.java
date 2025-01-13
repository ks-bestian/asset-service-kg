package kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthMenuVo extends ComDefaultVO {
    private Long seq;
    private Long authId;
    private Long menuId;
    private Long menuAuth;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private boolean menuChecked;
    private List<AuthMenuVo> chlidren = new ArrayList<>();
}
