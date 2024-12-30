package kr.co.bestiansoft.ebillservicekg.login.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;

@Mapper
public interface LoginMapper {
	
	LoginUserVo selectUser(String userId);

}
