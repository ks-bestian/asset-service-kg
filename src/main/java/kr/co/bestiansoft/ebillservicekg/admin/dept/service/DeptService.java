package kr.co.bestiansoft.ebillservicekg.admin.dept.service;

import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;

import java.util.HashMap;
import java.util.List;

public interface DeptService {
    List<DeptVo> getComDeptList(HashMap<String, Object> param);
    DeptVo getComDeptById(String deptCd);
}
