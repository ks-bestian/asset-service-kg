package kr.co.bestiansoft.ebillservicekg.admin.authUser.repository;

import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthUserMapper {
    List<AuthUserVo> getAuthUserList(Long authId);
    int createAuthUser(AuthUserVo authUserVo);
    void deleteAuthMenu(AuthUserVo authUserVo);
    boolean existsByAuthIdAndUserId(Long authId, String userId);
}
