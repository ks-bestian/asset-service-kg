package kr.co.bestiansoft.ebillservicekg.admin.authDept.service;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;

import java.util.List;

public interface AuthDeptService {
    List<AuthDeptVo> getAuthDeptList(String deptCd);
    AuthDeptCreate createAuthDept(AuthDeptCreate authDeptCreate);
}
