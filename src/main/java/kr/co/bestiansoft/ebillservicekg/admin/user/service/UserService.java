package kr.co.bestiansoft.ebillservicekg.admin.user.service;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    List<UserVo> getUserList(HashMap<String, Object> param);
    UserVo getUserDetail(Long seq);
    UserVo createUser(UserVo userVo);
    int updateUser(UserVo userVo);
    void deleteUser(List<String> seq);
    List<UserVo> getUserByDept(HashMap<String, Object> param);
}
