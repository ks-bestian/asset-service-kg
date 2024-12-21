package kr.co.bestiansoft.ebillservicekg.login.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.login.vo.LoginVo;

@Mapper
public interface LoginMapper {
	
	LoginVo selectUser(String userId);

}
