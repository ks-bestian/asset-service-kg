package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.UnauthorizedException;
import kr.co.bestiansoft.ebillservicekg.login.vo.Account;
import lombok.Data;

@Data
public class SecurityInfoUtil {    
	
    private Authentication authentication;
    private final Account account;
    
    public SecurityInfoUtil() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            account = (Account) authentication.getPrincipal();
        } else {
            throw new UnauthorizedException("Unauthorized access");
        }
    }
    
    public String getAccountId() {
		return account.getUserId();
	}
	
	public String getDeptCd() {
		return account.getDeptCd();
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		return account.getAuthorities();
	}
    
}
