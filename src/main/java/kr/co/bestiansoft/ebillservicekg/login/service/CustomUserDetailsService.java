package kr.co.bestiansoft.ebillservicekg.login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import kr.co.bestiansoft.ebillservicekg.login.repository.LoginMapper;
import kr.co.bestiansoft.ebillservicekg.login.vo.Account;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final LoginMapper loginMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LoginUserVo user = loginMapper.selectUser(username);
		if(user == null) {
			return null;
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		List<AuthVo> authList = loginMapper.selectUserAuth(username);
		if(authList != null) {
			for(AuthVo auth : authList) {
				grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(auth.getAuthId())));
			}
		}
		
		return new Account(user, grantedAuthorities);
		
	}

}
