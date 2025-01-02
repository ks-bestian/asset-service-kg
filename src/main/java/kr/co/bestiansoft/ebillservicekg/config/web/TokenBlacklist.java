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

    // 필요에 따라 블랙리스트에서 토큰을 제거하는 메서드도 추가할 수 있습니다
    public void removeFromBlacklist(String token) {
        blacklist.remove(token);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}