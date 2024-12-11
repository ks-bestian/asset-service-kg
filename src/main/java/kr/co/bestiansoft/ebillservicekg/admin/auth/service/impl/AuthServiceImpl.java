package kr.co.bestiansoft.ebillservicekg.admin.auth.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.auth.repository.AuthMapper;
import kr.co.bestiansoft.ebillservicekg.admin.auth.service.AuthService;
import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    @Override
    public List<AuthVo> getAuthList(HashMap<String, Object> param) {
        return authMapper.getAuthList(param);
    }

    @Override
    public AuthVo getAuthDetail(Long authId) {
        return authMapper.getAuthDetail(authId);
    }

    @Override
    public AuthVo createAuth(AuthVo authVo) {
        authMapper.insertAuth(authVo);
        return authVo;
    }

    @Override
    public int updateAuth(AuthVo authVo) {
        return authMapper.updateAuth(authVo);
    }

    @Override
    public void deleteAuth(List<Long> ids) {
        for(Long id : ids) {
            authMapper.deleteAuth(id);
        }
    }
}
