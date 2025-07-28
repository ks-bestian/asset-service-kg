package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService {

    private final UserMapper userMapper;
    private final ComFileService comFileService;
    private final EDVHelper edv;

    /**
     * Retrieves detailed information about the currently authenticated user.
     * The user ID is fetched from the security context and added to the parameter map
     * before querying the underlying user data source.
     *
     * @param param A map containing parameters required for querying user information.
     *              The "userId" of the currently authenticated user is added to this map.
     * @return A UserMemberVo object containing detailed information about the user,
     *         such as user ID, name, department, profile image path, and other related attributes.
     */
    @Override
    public UserMemberVo getMyInfo(HashMap<String, Object> param) {
        param.put("userId", new SecurityInfoUtil().getAccountId());
        return userMapper.userDetail(param);
    }

    /**
     * Retrieves the content of a file based on the given path.
     * The method uses the currently authenticated user's ID to fetch user details
     * and attempts to retrieve the file content if a valid profile image path is provided.
     *
     * @param param A map containing parameters for file retrieval. The map is updated
     *              with the current user's ID before querying for the file.
     * @return An InputStream of the file content if available; otherwise, null.
     */
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


    /**
     * Updates the personal information of the currently authenticated user.
     * Includes functionalities to save profile image files, encrypt passwords,
     * and update information based on the user type (member or user).
     *
     * @param userMemberVo An object containing the user's all personal information
     *                     such as user details, profile image files, password,
     *                     and user type (member/user).
     * @return A UserMemberVo object reflecting the updated user information.
     */
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
