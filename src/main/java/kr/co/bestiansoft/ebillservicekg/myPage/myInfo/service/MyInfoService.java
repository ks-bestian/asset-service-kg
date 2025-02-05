package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;

import java.io.InputStream;
import java.util.HashMap;

public interface MyInfoService {
    UserMemberVo getMyInfo(HashMap<String, Object> param);

    InputStream getFileContentByPath(HashMap<String, Object> param);

    UserMemberVo updateMyInfo(UserMemberVo userMemberVo);

}
