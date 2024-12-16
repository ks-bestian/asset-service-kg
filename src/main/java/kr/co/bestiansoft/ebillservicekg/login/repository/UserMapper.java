package kr.co.bestiansoft.ebillservicekg.login.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.login.vo.UserVo;

@Mapper
public interface UserMapper {
	
	UserVo selectUser(String userId);

}
