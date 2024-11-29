package kr.co.bestiansoft.ebillservicekg.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    private final LoginService loginService;
//
//    @ApiOperation(value = "jwt 로그인", notes = "로그인 및 세션 생성")
//    @PostMapping("/api/authenticate")
//    public ResponseEntity<LoginResponse> authorize(@RequestBody @Valid LoginCondition loginCondition) {
//
//        boolean result = false;
//        String message ="";
//
//        try {
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginCondition.getUserId(), loginCondition.getPassword());
//
//            // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//            // 해당 객체를 SecurityContextHolder에 저장하고
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
//            String jwt = tokenProvider.createToken(authentication);
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            // response header에 jwt token에 넣어줌
//
//            ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", jwt)
//                                                     .httpOnly(true)
//                                                     .secure(true) // HTTPS를 사용하는 경우에만
//                                                     .path("/")
//                                                     .maxAge(24 * 60 * 60L) // 쿠키의 유효 기간 설정 (1일)
//                                                     .build();
//
//            httpHeaders.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
//
//            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//
//            LoginResponse loginResponse = loginService.getLoginInfo(result, message, jwt);
//
//
//            return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
//
//        } catch (BadCredentialsException e) {
//            log.warn("BadCredentialsException : {}", e.getMessage());
//            log.warn("", e);
//
//            message = "LOGIN_FAIL";
//            result = false;
//            return new ResponseEntity<>(LoginResponse.from(result, message), HttpStatus.UNAUTHORIZED);
//        } catch (AuthenticationException e) {
//            log.warn("AuthenticationException : {}", e.getMessage());
//            log.warn("", e);
//            message = "AUTH_FAIL";
//            result = false;
//            return new ResponseEntity<>(LoginResponse.from(result, message), HttpStatus.UNAUTHORIZED);
//        }
//    }
	
	@ApiOperation(value = "jwt 로그인", notes = "로그인 및 세션 생성")
	@PostMapping("/api/authenticate")
	public ResponseEntity<?> authorize(HttpServletRequest request) {
//		Account account = null;
		request.getSession().setAttribute("account", null);
		return ResponseEntity.ok().build();
	}
}
