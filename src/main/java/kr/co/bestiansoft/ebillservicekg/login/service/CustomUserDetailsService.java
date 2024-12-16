package kr.co.bestiansoft.ebillservicekg.login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.login.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.login.vo.UserVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserVo user = userMapper.selectUser(username);
		if(user == null) {
			return null;
		}
		// 임시 비밀번호
		String password = passwordEncoder.encode("best1234");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		return new User(username, password, grantedAuthorities);
	}

}
