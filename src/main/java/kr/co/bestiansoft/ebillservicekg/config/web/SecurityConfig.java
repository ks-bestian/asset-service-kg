package kr.co.bestiansoft.ebillservicekg.config.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final TokenProvider tokenProvider;
    private final TokenBlacklist tokenBlacklist;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().cors();

		http.exceptionHandling()
	        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .accessDeniedHandler(jwtAccessDeniedHandler);

		http.sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests((authorize) -> authorize
                .antMatchers(
                        "/v3/api-docs",
                        "/swagger*/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/com/file/pdf").permitAll()
                .antMatchers("/com/file/down").permitAll()
                .antMatchers("/ws").permitAll()
                .anyRequest().authenticated()
        );

        http.apply(new JwtSecurityConfig(tokenProvider, tokenBlacklist, authenticationManagerBuilder)); // JwtFilter to registe addFilterBefore JwtSecurityConfig class application


        // log out treatment
        http.logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .logoutSuccessHandler((request, response, authentication) -> {
	            // Logic to be processed in the success of logout (for example, token blacklist)
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

        return http.build();
    }

	@Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

	@Bean
    public HttpSessionSecurityContextRepository securityContextRepository() {
        HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        repository.setSpringSecurityContextKey(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new Sha256PasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
    }

}