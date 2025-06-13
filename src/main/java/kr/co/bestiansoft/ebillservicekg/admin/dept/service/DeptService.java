package kr.co.bestiansoft.ebillservicekg.admin.dept.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface DeptService {
    List<DeptVo> getComDeptList(HashMap<String, Object> param);
    List<DeptVo> getCmitList(HashMap<String, Object> param);
    ArrayNode getDeptTree(HashMap<String, Object> param);
    DeptVo getComDeptById(String deptCd, String lang);
    DeptVo createDept(DeptVo deptVo);
    DeptVo saveUsersCcofs(HashMap<String, Object> params);
    int updateDept(DeptVo deptVo);
    void deleteDept(List<String> deptCd);
    void deleteCmit(String deptCd, List<String> userIds);
}
