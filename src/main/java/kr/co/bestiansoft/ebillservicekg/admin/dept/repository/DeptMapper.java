package kr.co.bestiansoft.ebillservicekg.admin.dept.repository;

import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DeptMapper {
    List<DeptVo> getComDeptList(HashMap<String, Object> param);
    DeptVo getComDeptById(String deptCd);
}
