package kr.co.bestiansoft.ebillservicekg.admin.user.repository;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getUserList(HashMap<String, Object> param);
    UserVo getUserDetail(Long seq);
    int insertUser(UserVo userVo);
    int updateUser(UserVo userVo);
    void deleteUser(Long seq);
}
