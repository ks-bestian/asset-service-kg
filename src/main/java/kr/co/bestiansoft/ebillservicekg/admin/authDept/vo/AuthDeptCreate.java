package kr.co.bestiansoft.ebillservicekg.admin.authDept.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthDeptCreate {
    private String deptCd;
    private List<AuthDeptVo> authDeptVos;
}
