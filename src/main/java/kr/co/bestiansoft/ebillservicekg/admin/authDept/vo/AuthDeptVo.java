package kr.co.bestiansoft.ebillservicekg.admin.authDept.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthDeptVo extends ComDefaultVO {
    private String deptCd;
    private Long menuCd;
    private boolean menuChecked;

    private List<AuthDeptVo> children = new ArrayList<>();
}
