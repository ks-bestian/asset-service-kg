package kr.co.bestiansoft.ebillservicekg.config.web;

import kr.co.bestiansoft.ebillservicekg.admin.log.service.LogService;
import kr.co.bestiansoft.ebillservicekg.config.security.TokenProvider;
import kr.co.bestiansoft.ebillservicekg.config.web.interceptor.JwtFilteringInterceptor;
import kr.co.bestiansoft.ebillservicekg.config.web.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LogService logService;
    private final TokenProvider tokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor(logService));
        registry.addInterceptor(new JwtFilteringInterceptor(tokenProvider));
    }
}
