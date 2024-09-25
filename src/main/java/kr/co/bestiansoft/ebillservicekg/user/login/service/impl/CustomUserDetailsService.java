package kr.co.bestiansoft.ebillservicekg.user.login.service.impl;

import kr.co.bestiansoft.ebillservicekg.user.login.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // <FIXME> 추후 account 정보를 가져오는 로직을 구현해야 함
//    private final LoginQueryRepository loginQueryRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public Account loadUserByUsername(final String username) throws UsernameNotFoundException{

//        return loginQueryRepository.getLoginInfo(username);
        return Account.builder().build();
    }

}