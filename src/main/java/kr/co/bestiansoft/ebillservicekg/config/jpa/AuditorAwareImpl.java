package kr.co.bestiansoft.ebillservicekg.config.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.empty();
        /*
        <FIXME> Spring Security 설정 후 주석 해제 필요
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getPrincipal)
                       .filter(Account.class::isInstance)
                       .map(principal -> {
                           Account account = (Account) principal;
                           log.info("Principal : {}", account);
                           return account.getAccountId();
                       });

        */
    }
}
