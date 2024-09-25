package kr.co.bestiansoft.ebillservicekg.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final TokenBlacklist tokenBlacklist;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    private final UserDetailsService userDetailsService;

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Sha256PasswordEncoder();
    }
	
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable().cors().and()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)


                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/saveRoute/**").permitAll() 
                .antMatchers("/api/validateToken").permitAll() 
                .antMatchers("/api/logout").permitAll() // 로그아웃 api
                .antMatchers("/api/login").permitAll() // 로그인 api
                .antMatchers("/api/authenticate").permitAll() // 로그인 api
                .antMatchers("/api/signup").permitAll() // 회원가입 api
                .antMatchers("/h2-console/**").permitAll()// h2-console, favicon.ico 요청 인증 무시
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/api/lngCode").permitAll() // 언어 코드 조회
                //.anyRequest().authenticated() // 그 외 인증 없이 접근X

                .and()
                .apply(new JwtSecurityConfig(tokenProvider, tokenBlacklist, authenticationManagerBuilder)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        // 로그아웃 처리
        /*
        httpSecurity.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
            .logoutSuccessHandler((request, response, authentication) -> {
                // 로그아웃 성공 시 처리할 로직 (예: 토큰 블랙리스트 추가)
                response.setStatus(HttpServletResponse.SC_OK);
            })
            .deleteCookies("jwtToken")
            .invalidateHttpSession(true)
            .permitAll();
        */
        httpSecurity.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
        .logoutSuccessHandler((request, response, authentication) -> {
            // 로그아웃 성공 시 처리할 로직 (예: 토큰 블랙리스트 추가)
    		String bearerToken = request.getHeader("Authorization");
    		String jwt ="";
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            	jwt = bearerToken.substring(7);
            }
    		
            tokenBlacklist.addToBlacklist(jwt);
            response.setStatus(HttpServletResponse.SC_OK);
        })
        .deleteCookies("jwtToken")
        .invalidateHttpSession(true)
        .permitAll();
        
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder())
                   .and()
                   .build();
    }

}