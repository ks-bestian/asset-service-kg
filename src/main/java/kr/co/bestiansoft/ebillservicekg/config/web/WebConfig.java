package kr.co.bestiansoft.ebillservicekg.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.config.web.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AcsHistService acsHistService;
//    private final TokenProvider tokenProvider;

    @Value("${security.allow.context-url}")
    private String allowedUrl;

    @Value("${security.allow.context-url2}")
    private String allowedUrl2;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor(acsHistService));
//        registry.addInterceptor(new JwtFilteringInterceptor(tokenProvider));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedUrl)
                .allowedOrigins(allowedUrl2)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD")
                .allowedHeaders("*")
        		.exposedHeaders("Upload-Offset", "Location", "Tus-Resumable", "Upload-Length", "Upload-Metadata");
//                .allowCredentials(true);
    }
}
