package kr.co.bestiansoft.ebillservicekg.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.config.web.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder
				.defaultHeaders(httpHeaders -> {
					httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				})
				.build();
	}
}
