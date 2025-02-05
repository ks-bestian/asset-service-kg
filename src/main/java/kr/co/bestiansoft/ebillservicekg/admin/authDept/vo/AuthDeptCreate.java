package kr.co.bestiansoft.ebillservicekg.admin.authDept.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthDeptCreate {
    private String deptCd;
    private List<AuthDeptVo> authDeptVos;
}
