package kr.co.bestiansoft.ebillservicekg.admin.dept.repository;

import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DeptMapper {
    List<DeptVo> selectListDept(HashMap<String, Object> param);
    DeptVo selectDept(String deptCd);
    int insertDept(DeptVo deptVo);
    int updateDept(DeptVo deptVo);
    void deleteDept(String deptCd);
}
