package kr.co.bestiansoft.ebillservicekg.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.config.web.JwtFilter;
import kr.co.bestiansoft.ebillservicekg.config.web.TokenProvider;
import kr.co.bestiansoft.ebillservicekg.login.vo.Account;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private HttpSessionSecurityContextRepository securityContextRepository;
	
	private final TokenProvider tokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRead, HttpServletRequest req, HttpServletResponse res) {
		
		boolean result = false;
    	String msg ="";

        try {

	        UsernamePasswordAuthenticationToken authenticationToken =
	                new UsernamePasswordAuthenticationToken(loginRead.getUserId(), loginRead.getPswd());

	        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);

	        // 해당 객체를 SecurityContextHolder에 저장하고
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
	        String token = tokenProvider.createToken(authentication);

	        HttpHeaders httpHeaders = new HttpHeaders();
	        // response header에 jwt token에 넣어줌

	        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
	                .httpOnly(true)
	                .secure(true) // HTTPS를 사용하는 경우에만
	                .path("/")
	                .maxAge(24 * 60 * 60) // 쿠키의 유효 기간 설정 (1일)
	                .build();

	        httpHeaders.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
	        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

	        msg = "login success";
	        result = true;
	        Account account = new SecurityInfoUtil().getAccount();
	        return new ResponseEntity<>(LoginResponse.from(result, msg, token, account), httpHeaders, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            msg = "LOGIN_FAIL";
            result = false;
            return new ResponseEntity<>(LoginResponse.from(result, msg), HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            msg = "AUTH_FAIL";
            result = false;
            return new ResponseEntity<>(LoginResponse.from(result, msg), HttpStatus.UNAUTHORIZED);
        }
        
	}
	
}
