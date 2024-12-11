package kr.co.bestiansoft.ebillservicekg.admin.auth.service;

import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;

import java.util.HashMap;
import java.util.List;

public interface AuthService {

    List<AuthVo> getAuthList(HashMap<String, Object> param);
    AuthVo getAuthDetail(Long authId);
    AuthVo createAuth(AuthVo authVo);
    int updateAuth(AuthVo authVo);
    void deleteAuth(List<Long> ids);
}
