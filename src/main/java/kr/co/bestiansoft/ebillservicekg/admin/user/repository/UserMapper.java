package kr.co.bestiansoft.ebillservicekg.admin.user.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

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
    void updateCmit(String userId);
    void updateJob(HashMap<String, Object> param);
}
