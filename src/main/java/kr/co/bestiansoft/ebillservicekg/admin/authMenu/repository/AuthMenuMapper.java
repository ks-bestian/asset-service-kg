package kr.co.bestiansoft.ebillservicekg.admin.authMenu.repository;

import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMenuMapper {
    List<AuthMenuVo> getAuthMenuList(Long authId);
    int insertAuthMenu(AuthMenuVo authMenuVo);
    void deleteAuthMenu(Long authId);
}
