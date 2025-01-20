package kr.co.bestiansoft.ebillservicekg.admin.dept.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface DeptService {
    List<DeptVo> getComDeptList(HashMap<String, Object> param);
    ArrayNode getDeptTree(HashMap<String, Object> param);
    DeptVo getComDeptById(String deptCd);
    DeptVo createDept(DeptVo deptVo);
    int updateDept(DeptVo deptVo);
    void deleteDept(List<String> deptCd);
}
