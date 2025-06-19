package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService {

    private final UserMapper userMapper;
    private final ComFileService comFileService;
    private final EDVHelper edv;

    @Override
    public UserMemberVo getMyInfo(HashMap<String, Object> param) {
        param.put("userId", new SecurityInfoUtil().getAccountId());
        return userMapper.userDetail(param);
    }


    @Override
    public InputStream getFileContentByPath(HashMap<String, Object> param) {
        try {
            param.put("userId", new SecurityInfoUtil().getAccountId());
            UserMemberVo myInfo = userMapper.userDetail(param);
            InputStream resource = null;
            if (myInfo.getProfileImgPath() != null && !myInfo.getProfileImgPath().equals("")) {
//                resource = Files.readAllBytes(Paths.get(myInfo.getProfileImgPath()));
            	List<ComFileVo> fileList = comFileService.getFileList(myInfo.getProfileImgPath());
            	if(fileList != null && fileList.size() > 0) {
            		String fileId = fileList.get(0).getFileId();
               		resource = edv.download(fileId);	
            	}
            }
            return resource;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public UserMemberVo updateMyInfo(UserMemberVo userMemberVo) {
        if(userMemberVo.getFiles() != null) {
            String fileGroupId = comFileService.saveFileList(userMemberVo.getFiles());
            userMemberVo.setProfileImgPath(fileGroupId);
        }

        if(userMemberVo.getUserPassword() != null) {
            userMemberVo.setUserPassword(LoginRequest.getSha256(userMemberVo.getUserPassword()));
        }

        if (userMemberVo.getType().equals("member")) {
        	userMemberVo.setMemberId(new SecurityInfoUtil().getAccountId());
            userMapper.updateMyInfoMember(userMemberVo);
        } else if(userMemberVo.getType().equals("user")){
        	userMemberVo.setUserId(new SecurityInfoUtil().getAccountId());
            userMapper.updateMyInfoUser(userMemberVo);
        }

        userMemberVo.setFiles(null);
        return userMemberVo;
    }
}
