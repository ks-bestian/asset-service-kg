package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService {

    private final UserMapper userMapper;
    private final ComFileService comFileService;

    @Override
    public UserMemberVo getMyInfo(HashMap<String, Object> param) {
        param.put("userId", new SecurityInfoUtil().getAccountId());
        return userMapper.userDetail(param);
    }


    @Override
    public byte[] getFileContentByPath(HashMap<String, Object> param) {
        try {
            param.put("userId", new SecurityInfoUtil().getAccountId());
            UserMemberVo myInfo = userMapper.userDetail(param);
            byte[] resource = null;
            if (myInfo.getProfileImgPath() != null && !myInfo.getProfileImgPath().equals("")) {
                resource = Files.readAllBytes(Paths.get(myInfo.getProfileImgPath()));
            }
            return resource;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public UserMemberVo updateMyInfo(UserMemberVo userMemberVo) {
        if(userMemberVo.getFiles() != null) {
            String fileGroupId = comFileService.saveFile(userMemberVo.getFiles());
            userMemberVo.setProfileImgPath(fileGroupId);
        }

        if (userMemberVo.getType().equals("member")) {
            userMapper.updateMyInfoMember(userMemberVo);
        } else if(userMemberVo.getType().equals("user")){
            userMapper.updateMyInfoUser(userMemberVo);
        }

        userMemberVo.setFiles(null);
        return userMemberVo;
    }
}
