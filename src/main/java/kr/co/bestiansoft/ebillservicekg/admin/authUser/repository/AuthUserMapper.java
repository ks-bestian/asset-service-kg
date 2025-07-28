package kr.co.bestiansoft.ebillservicekg.admin.authUser.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;

@Mapper
public interface AuthUserMapper {
    List<AuthUserVo> selectListAuthUser(HashMap<String, Object> param);
    int createAuthUser(AuthUserVo authUserVo);
    void deleteAuthUser(Long authId);
}
