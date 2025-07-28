package kr.co.bestiansoft.ebillservicekg.admin.authDept.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;

public interface AuthDeptService {
    List<AuthDeptVo> getAuthDeptList(String deptCd);
    AuthDeptCreate createAuthDept(AuthDeptCreate authDeptCreate);
}
