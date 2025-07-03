package kr.co.bestiansoft.ebillservicekg.config.web;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.login.service.CustomUserDetailsService;
import kr.co.bestiansoft.ebillservicekg.login.vo.Account;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private Key key;

    // In order to assign a Secret value to a variable after bin is generated and injected
    @Override
    public void afterPropertiesSet() {
    	
    	this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        // Set the expire time of the token
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds*1000);

        Account account = (Account)authentication.getPrincipal();
        
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("deptCd", account.getDeptCd())
                .claim("deptHeadYn", account.getDeptHeadYn())
                .claim(AUTHORITIES_KEY, authorities) // Information storage
                .signWith(key, SignatureAlgorithm.HS256) //Use the encryption algorithm and set the secret value for the signature.
                .setExpiration(validity) // If the "set Expire Time" option is not specified, expiration will not occur.
                .compact();
    }

    // Create a claim with tokens, use it to create a user object, and finally return the authentication object.
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        String userId = claims.getSubject();
        String deptCd = Objects.toString(claims.get("deptCd"), null);
        String deptHeadYn = Objects.toString(claims.get("deptHeadYn"), null);
        String authorities = Objects.toString(claims.get(AUTHORITIES_KEY), null);
        
        LoginUserVo user = new LoginUserVo();
        user.setUserId(userId);
        user.setDeptCd(deptCd);
        user.setDeptHeadYn(deptHeadYn);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if(!StringUtil.isNullOrEmpty(authorities)) {
        	String[] authList = authorities.split(",");
			for(String auth : authList) {
				if(!StringUtil.isNullOrEmpty(auth)) {
					grantedAuthorities.add(new SimpleGrantedAuthority(auth));	
				}
			}
        }
        
        Account accont = new Account(user, grantedAuthorities);
        
//        User principal = new User(claims.getSubject(), "", new ArrayList<>());
//		UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(accont, null, grantedAuthorities);

    }

    // Perform the validation of tokens
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            
            logger.info("This is the wrong JWT signature.");
        } catch (ExpiredJwtException e) {
            
            logger.info("This is the expired JWT token.");
        } catch (UnsupportedJwtException e) {
            
            logger.info("This is not supported JWT token.");
        } catch (IllegalArgumentException e) {
            
            logger.info("JWT tokens are wrong.");
        }
        return false;
    }
}