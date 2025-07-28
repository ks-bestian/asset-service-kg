package kr.co.bestiansoft.ebillservicekg.login.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginUserVo;

@Mapper
public interface LoginMapper {

	LoginUserVo selectUser(String userId);
	List<AuthVo> selectUserAuth(String userId);
	List<ComCodeDetailVo> selectListComCodeAll();

}
