package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MyInfoUpdate {
    private String userType;
    private String userId;
    private String pswd;
    private String msgRcvYn;
    private String imgPath;
    private List<MultipartFile> imgFile;

}
