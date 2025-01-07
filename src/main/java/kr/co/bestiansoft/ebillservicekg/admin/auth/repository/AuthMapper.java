package kr.co.bestiansoft.ebillservicekg.admin.auth.repository;

import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AuthMapper {
    List<AuthVo> selectListAuth(HashMap<String, Object> param);
    AuthVo selectAuth(Long authId);
    int insertAuth(AuthVo authVo);
    int updateAuth(AuthVo authVo);
    void deleteAuth(Long id);

}
