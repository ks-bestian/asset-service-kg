package kr.co.bestiansoft.ebillservicekg.admin.dept.service;

import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface DeptService {
    List<DeptVo> getComDeptList(HashMap<String, Object> param);
    DeptVo getComDeptById(String deptCd);
    DeptVo createDept(DeptVo deptVo);
    int updateDept(DeptVo deptVo);
    void deleteDept(String deptCd);
}
