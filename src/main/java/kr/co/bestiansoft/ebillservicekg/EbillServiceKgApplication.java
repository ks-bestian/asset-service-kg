package kr.co.bestiansoft.ebillservicekg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class EbillServiceKgApplication {

    public static void main(String[] args) {

        log.info("EbillServiceKgApplication start >>");
        SpringApplication.run(EbillServiceKgApplication.class, args);
    }


}
