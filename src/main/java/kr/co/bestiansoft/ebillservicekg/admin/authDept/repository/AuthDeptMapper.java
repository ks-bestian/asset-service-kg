package kr.co.bestiansoft.ebillservicekg.admin.authDept.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;

@Mapper
public interface AuthDeptMapper {
    List<AuthDeptVo> selectListAuthDept(String deptCd);
    void deleteAuthDept(String deptCd);

    int insertAuthDept(AuthDeptVo authDeptVo);
}
