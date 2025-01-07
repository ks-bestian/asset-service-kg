package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MyInfoUpdate {
    private String userType;
    private String userId;
    private String pswd;
    private String msgRcvYn;
    private String imgPath;
    private List<MultipartFile> imgFile;

}
