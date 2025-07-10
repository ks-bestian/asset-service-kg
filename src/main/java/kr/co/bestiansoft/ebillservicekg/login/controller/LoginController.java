package kr.co.bestiansoft.ebillservicekg.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl.CcofService;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.config.web.JwtFilter;
import kr.co.bestiansoft.ebillservicekg.config.web.TokenBlacklist;
import kr.co.bestiansoft.ebillservicekg.config.web.TokenProvider;
import kr.co.bestiansoft.ebillservicekg.login.repository.LoginMapper;
import kr.co.bestiansoft.ebillservicekg.login.service.CustomUserDetailsService;
import kr.co.bestiansoft.ebillservicekg.login.vo.Account;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginResponse;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
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
	private final TokenBlacklist tokenBlacklist;

	private final LoginMapper loginMapper;
	private final ProcessMapper processMapper;

	private final BaseCodeService basecodeService;
	private final CcofService ccofService;

	private final CustomUserDetailsService customUserDetailsService;



	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest req,HttpServletResponse rep) {

		String bearerToken = req.getHeader("Authorization");
		String jwt ="";
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	jwt = bearerToken.substring(7);
        	tokenBlacklist.addToBlacklist(jwt);
            rep.setStatus(HttpServletResponse.SC_OK);

            return ResponseEntity.ok(HttpStatus.OK);

        }

	    return new ResponseEntity<>(rep, HttpStatus.UNAUTHORIZED);
	}




	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRead, HttpServletRequest req, HttpServletResponse res) {

		boolean result = false;
    	String msg ="";

        try {

	        UsernamePasswordAuthenticationToken authenticationToken =
	                new UsernamePasswordAuthenticationToken(loginRead.getUserId(), loginRead.getPswd());

	        // authenticate When the method is executed, the LoaduserByusername method of the CustomUserdetailsService Class executes
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);

	        //Save the object in SecurityContextHolder
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // authenticationCreate a JWT TOKEN through the CreateToken method
	        String token = tokenProvider.createToken(authentication);

	        HttpHeaders httpHeaders = new HttpHeaders();
	        // Add the JWT token to the response header.

	        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
	                .httpOnly(true)
	                .secure(true) // Only when using https
	                .path("/")
	                .maxAge(24 * 60 * 60) // Effective period of cookies (1 day)
	                .build();

	        httpHeaders.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());
	        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

	        msg = "login success";
	        result = true;
	        Account account = new SecurityInfoUtil().getAccount();

	        LoginResponse rep = new LoginResponse();

	        List<ComCodeDetailVo> comCodes = loginMapper.selectListComCodeAll();
	        List<BaseCodeVo> baseCodes = basecodeService.getBaseCodeList(new HashMap<>());
	        HashMap<String, Object> param = new HashMap<>();
	        param.put("userId", account.getUserId());
	        List<CcofVo> ccofList = ccofService.getCcofList(param);

	        rep.setResult(result);
	        rep.setMsg(msg);
	        rep.setToken(token);

	        String lang = loginRead.getLang();
	        if("lng_type_1".equals(lang)) {
	        	account.setDeptNm(account.getDeptNmKg());
	        } else if("lng_type_2".equals(lang)) {
	        	account.setDeptNm(account.getDeptNmRu());
	        } else {
	        	account.setDeptNm(account.getDeptNmKo());
	        }
	        rep.setLoginInfo(account);
	        rep.setComCodes(comCodes);
	        rep.setBaseCodes(baseCodes);
	        rep.setCcofList(ccofList);

	        return new ResponseEntity<>(rep, httpHeaders, HttpStatus.OK);

        } catch (BadCredentialsException e) {
        	LoginResponse rep = new LoginResponse();
	        rep.setResult(false);
	        rep.setMsg("LOGIN_FAIL");
            return new ResponseEntity<>(rep, HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
        	LoginResponse rep = new LoginResponse();
	        rep.setResult(false);
	        rep.setMsg("AUTH_FAIL");

            return new ResponseEntity<>(rep, HttpStatus.UNAUTHORIZED);
        }

	}

	@PostMapping("/changedept")
	public ResponseEntity<?> changedept(@RequestBody LoginUserVo loginUserVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		Account account = (Account)customUserDetailsService.loadUserByUsername(userId);
		String orgDeptCd = account.getDeptCd();

		HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
		List<CcofVo> ccofList = ccofService.getCcofList(param);
		boolean ok = orgDeptCd.equals(loginUserVo.getDeptCd());
		for(CcofVo ccof : ccofList) {
			if(ccof.getDeptCd().equals(loginUserVo.getDeptCd())) {
				ok = true;
			}
		}
		if(!ok) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		new SecurityInfoUtil().getAccount().setDeptCd(loginUserVo.getDeptCd());

		Authentication authentication = new SecurityInfoUtil().getAuthentication();
		String token = tokenProvider.createToken(authentication);

		LoginResponse rep = new LoginResponse();
		rep.setToken(token);
		return new ResponseEntity<>(rep, HttpStatus.OK);
	}

	@PostMapping("/token")
	public ResponseEntity<?> token() {
		Authentication authentication = new SecurityInfoUtil().getAuthentication();
		String token = tokenProvider.createToken(authentication);

		LoginResponse rep = new LoginResponse();
		rep.setToken(token);
		return new ResponseEntity<>(rep, HttpStatus.OK);
	}
}
