package kr.co.bestiansoft.ebillservicekg.admin.authMenu.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;

@Mapper
public interface AuthMenuMapper {
    List<AuthMenuVo> selectListAuthMenu(Long authId);
    int insertAuthMenu(AuthMenuVo authMenuVo);
    void deleteAuthMenu(Long authId);
}
