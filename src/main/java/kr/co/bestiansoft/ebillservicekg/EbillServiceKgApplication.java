package kr.co.bestiansoft.ebillservicekg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger.web.SecurityConfiguration;

@Slf4j
@SpringBootApplication(exclude = SecurityConfiguration.class) // <FIXME> Spring Security 설정 후 exclude 제거
public class EbillServiceKgApplication {

    public static void main(String[] args) {

        log.info("EbillServiceKgApplication start >>");
        SpringApplication.run(EbillServiceKgApplication.class, args);
    }

}
