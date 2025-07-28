package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service;

import java.io.InputStream;
import java.util.HashMap;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;

public interface MyInfoService {
    UserMemberVo getMyInfo(HashMap<String, Object> param);

    InputStream getFileContentByPath(HashMap<String, Object> param);

    UserMemberVo updateMyInfo(UserMemberVo userMemberVo);

}
