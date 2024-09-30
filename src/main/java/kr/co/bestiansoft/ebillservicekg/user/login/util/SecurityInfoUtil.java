package kr.co.bestiansoft.ebillservicekg.user.login.util;

import kr.co.bestiansoft.ebillservicekg.common.exceptionAdvice.exception.UnauthorizedException;
import kr.co.bestiansoft.ebillservicekg.user.employee.repository.entity.EmployeeEntity;
import kr.co.bestiansoft.ebillservicekg.user.login.domain.Account;
import kr.co.bestiansoft.ebillservicekg.user.member.repository.entity.MemberEntity;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
		return account.getAccountId();
	}
	
	public String getAccountType() {
		return account.getAccountType();
	}
	
	public String getDeptCd() {
		return account.getDeptCd();
	}
	
	public String getDeptNm1() {
		return account.getDeptNm1();
	}
	public String getDeptNm2() {
		return account.getDeptNm2();
	}

	public String getShrtNm() {
		return account.getShrtNm();
	}
	
	public String getUprDeptId() {
		return account.getUprDeptId();
	}
	
	public MemberEntity getMember() {
		
    	if(account.getAccountType().equals("member")) {
    		return account.getMember();
    	}else {
    		return null;
    	}
    	
	}
	
	public EmployeeEntity getEmployee() {
		
    	if(account.getAccountType().equals("user")) {
    		return account.getEmployee();
    	}else {
    		return null;
    	}
	}
	
	

}
