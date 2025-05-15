package kr.co.bestiansoft.ebillservicekg.admin.user.repository;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> selectListUser(HashMap<String, Object> param);
    List<UserVo> selectListUserByDept(HashMap<String, Object> param);
    UserMemberVo userDetail(HashMap<String, Object> param);
    int updatePswd(HashMap<String, Object> param);
    UserVo selectUser(Long seq);
    int insertUser(UserVo userVo);
    int updateUser(UserVo userVo);
    void deleteUser(String userId);

    int updateMyInfoMember(UserMemberVo userMemberVo);
    int updateMyInfoUser(UserMemberVo userMemberVo);
}
