package kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo;

import java.util.ArrayList;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AuthMenuVo extends ComDefaultVO {
    private Long seq;
    private Long authId;
    private Long menuId;
    private Long menuAuth;
    private boolean menuChecked;
    private List<AuthMenuVo> chlidren = new ArrayList<>();
}
