package kr.co.bestiansoft.ebillservicekg.sed_jk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CdsClientConfig {
    @Value("${app.cds.url}")
    private String baseUrl;

    // В документации указан User-Agent с именем организации [cite: 11]
    // Он нужен для заголовка User-Agent. [cite: 11]
    @Value("${app.cds.user-agent-org-name}")
    private String userAgentOrgName;

    @Bean
    public RestClient eapRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", userAgentOrgName) // Устанавливаем User-Agent по умолчанию [cite: 11]
                .build();
    }
}
