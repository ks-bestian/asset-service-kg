package kr.co.bestiansoft.ebillservicekg.common.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.service.DateTimeHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeHolder implements DateTimeHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
