package kr.co.bestiansoft.ebillservicekg.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
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
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticate(String id, String password, HttpServletRequest req, HttpServletResponse res) {
		
		// authentication
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 인증된 SecurityContext 를 SessionRepository 와 요청과 응답에 저장
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, req, res);

        return ResponseEntity.ok("success");
	}
	
}
