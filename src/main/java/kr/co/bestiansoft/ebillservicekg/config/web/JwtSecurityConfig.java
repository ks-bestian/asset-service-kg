package kr.co.bestiansoft.ebillservicekg.config.web;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    private TokenBlacklist tokenBlacklist;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public JwtSecurityConfig(TokenProvider tokenProvider, TokenBlacklist tokenBlacklist, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.tokenBlacklist = tokenBlacklist;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    
    @Override
    public void configure(HttpSecurity http) {
    	JwtFilter customFilter = new JwtFilter(tokenProvider, tokenBlacklist);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}