package kr.co.bestiansoft.ebillservicekg.admin.user.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public List<UserVo> getUserList(HashMap<String, Object> param) {
        return userMapper.selectListUser(param);
    }

    @Override
    public UserVo getUserDetail(Long seq) {
        return userMapper.selectUser(seq);
    }

    @Override
    public UserVo createUser(UserVo userVo) {
        userMapper.insertUser(userVo);
        return userVo;
    }

    @Override
    public int updateUser(UserVo userVo) {
        return userMapper.updateUser(userVo);
    }

    @Override
    public void deleteUser(List<String> seq) {
        for (String id : seq) {
            userMapper.deleteUser(id);
        }
    }

    @Override
    public List<UserVo> getUserByDept(HashMap<String, Object> param) {
        return userMapper.selectListUserByDept(param);
    }
}
