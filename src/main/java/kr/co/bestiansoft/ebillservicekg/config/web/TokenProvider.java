package kr.co.bestiansoft.ebillservicekg.config.web;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import kr.co.bestiansoft.ebillservicekg.login.service.CustomUserDetailsService;
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

    // 빈이 생성되고 주입을 받은 후에 secret값을 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
    	
    	this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds*1000);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("test_key", "test_val")
                .claim(AUTHORITIES_KEY, authorities) // 정보 저장
                .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
                .compact();
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        User principal = new User(claims.getSubject(), "", new ArrayList<>());
//		UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

		return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());

    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}