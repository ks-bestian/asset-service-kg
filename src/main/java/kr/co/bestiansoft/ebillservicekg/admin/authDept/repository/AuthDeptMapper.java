package kr.co.bestiansoft.ebillservicekg.admin.authDept.repository;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthDeptMapper {
    List<AuthDeptVo> selectListAuthDept(String deptCd);
    void deleteAuthDept(String deptCd);

    int insertAuthDept(AuthDeptVo authDeptVo);
}
