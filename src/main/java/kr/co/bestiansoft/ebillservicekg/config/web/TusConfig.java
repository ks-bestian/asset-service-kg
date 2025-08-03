package kr.co.bestiansoft.ebillservicekg.config.web;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import me.desair.tus.server.TusFileUploadService;

@Configuration
public class TusConfig {

    @Value("${tus.server.data.expiration}")
    Long tusExpirationPeriod;

    @PreDestroy
    public void exit() throws IOException {
        //cleanup any expired uploads and stale locks
        tus().cleanup();
    }

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
                .withDownloadFeature()
                .withUploadExpirationPeriod(tusExpirationPeriod);

    }

}