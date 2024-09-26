package kr.co.bestiansoft.ebillservicekg.config.jpa;

import kr.co.bestiansoft.ebillservicekg.user.login.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getPrincipal)
                       .filter(Account.class::isInstance)
                       .map(principal -> {
                           Account account = (Account) principal;
                           log.info("Principal : {}", account);
                           return account.getAccountId();
                       });

    }
}
