package kr.co.bestiansoft.ebillservicekg.admin.user.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.PasswordUtill;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
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
    private final CcofMapper ccofMapper;
    private final MemberMapper memberMapper;

    @Override
    public List<UserVo> getUserList(HashMap<String, Object> param) {
        return userMapper.selectListUser(param);
    }

    @Override
    public UserVo getUserDetail(Long seq) {
        return userMapper.selectUser(seq);
    }

    public UserMemberVo getUserMemberDetail(String userId) {
        if(userId == null || userId.isEmpty()) { return null;}
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return userMapper.userDetail(map);
    }

    @Override
    public UserVo createUser(UserVo userVo) {
        String regId = new SecurityInfoUtil().getAccountId();
        userVo.setUserPassword(LoginRequest.getSha256(userVo.getUserPassword()));
        userVo.setRegId(regId);

        int ord = 1;

        List<String> ccofCds = userVo.getCcofCds();

        userMapper.insertUser(userVo);

        for (String deptCd : ccofCds) {
            userVo.setDeptCd(deptCd);
            userVo.setOrd(ord);
            if (deptCd != null) {
                ccofMapper.insertCcofInUser(userVo);
            }
            ord++;
        }

        return userVo;
    }

    @Override
    public int updateUser(UserVo userVo) {
        String modId = new SecurityInfoUtil().getAccountId();

        userVo.setModId(modId);
        userVo.setUserPassword(LoginRequest.getSha256(userVo.getUserPassword()));
        userMapper.updateUser(userVo);

        ccofMapper.deleteCcof(userVo.getUserId());
        int ord = 1;

        List<String> ccofCds = userVo.getCcofCds();

        for (String deptCd : ccofCds) {
            userVo.setDeptCd(deptCd);
            userVo.setOrd(ord);

            if(deptCd != null) {
                ccofMapper.insertCcofInUser(userVo);
            }
            ord++;
        }

        return 0;
    }

    @Override
    public void deleteUser(List<String> seq) {
        for (String id : seq) {
            userMapper.deleteUser(id);
            ccofMapper.deleteCcof(id);
        }
    }

    @Override
    public List<UserVo> getUserByDept(HashMap<String, Object> param) {
        return userMapper.selectListUserByDept(param);
    }

    @Override
    public String resetPswd(HashMap<String, Object> param) {
        String pswd  = PasswordUtill.generatePassword();

        param.put("userPassword", LoginRequest.getSha256(pswd));
        userMapper.updatePswd(param);
        return pswd;
    }

    @Override
    public void updatePswd(HashMap<String, Object> param) {
        UserMemberVo user = userMapper.userDetail(param);
        String pswd = LoginRequest.getSha256((String)param.get("userPassword"));
        param.put("userPassword", pswd);
        if(user.getType().equals("member")) {
            memberMapper.updatePswd(param);
        }else if(user.getType().equals("user")) {
            userMapper.updatePswd(param);
        }
    }

    @Override
    public void updateJob(HashMap<String, Object> param) {
        userMapper.updateJob(param);
    }
}
