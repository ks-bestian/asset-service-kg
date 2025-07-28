package kr.co.bestiansoft.ebillservicekg.admin.auth.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;

@Mapper
public interface AuthMapper {
    List<AuthVo> selectListAuth(HashMap<String, Object> param);
    AuthVo selectAuth(Long authId);
    int insertAuth(AuthVo authVo);
    int updateAuth(AuthVo authVo);
    void deleteAuth(Long id);

}
