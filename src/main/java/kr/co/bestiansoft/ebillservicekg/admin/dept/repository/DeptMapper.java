package kr.co.bestiansoft.ebillservicekg.admin.dept.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;

@Mapper
public interface DeptMapper {
    List<DeptVo> selectListDept(HashMap<String, Object> param);
    List<DeptVo> getCmitList(HashMap<String, Object> param);
    DeptVo selectDept(String deptCd, String lang);
    int insertDept(DeptVo deptVo);
    int updateDept(DeptVo deptVo);
    void deleteDept(String deptCd);
}
