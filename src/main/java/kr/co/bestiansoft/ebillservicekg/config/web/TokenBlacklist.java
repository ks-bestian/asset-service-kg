package kr.co.bestiansoft.ebillservicekg.config.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklist implements InitializingBean{
    private Set<String> blacklist = new HashSet<>();

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    // You can also add methods to remove tokens from the blacklist as needed. 
    public void removeFromBlacklist(String token) {
        blacklist.remove(token);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}