package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService {

    private final UserMapper userMapper;
    @Override
    public UserMemberVo getMyInfo(HashMap<String, Object> param) {
        param.put("userId", new SecurityInfoUtil().getAccountId());
        return userMapper.userDetail(param);
    }
}
