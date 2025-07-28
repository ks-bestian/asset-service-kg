package kr.co.bestiansoft.ebillservicekg.admin.user.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

public interface UserService {
    List<UserVo> getUserList(HashMap<String, Object> param);
    UserVo getUserDetail(Long seq);
    UserVo createUser(UserVo userVo);
    int updateUser(UserVo userVo);
    void deleteUser(List<String> seq);
    String resetPswd(HashMap<String, Object> param);
    void updatePswd(HashMap<String, Object> param);
    void updateJob(HashMap<String, Object> param);
    List<UserVo> getUserByDept(HashMap<String, Object> param);
    UserMemberVo getUserMemberDetail(String userId);
}
