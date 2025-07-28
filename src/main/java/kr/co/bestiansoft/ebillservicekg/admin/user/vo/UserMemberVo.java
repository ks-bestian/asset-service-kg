package kr.co.bestiansoft.ebillservicekg.admin.user.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserMemberVo {
    private String userId;
    private String userNm;
    private String userNmKg;
    private String userNmRu;
    private String memberId;
    private String memberNmKg;
    private String memberNmRu;
    private String deptCd;
    private String deptNm;
    private String email;
    private String genCd;
    private String profileImgPath;
    private String msgRcvYn;
    private String docMgrYn;
    private String deptHeadYn;
    private String jobCd;
    private String jobNm;
    private String posCd;
    private String ageCd;
    private String cmitCd;
    private String cmitNm;
    private String polyCd;
    private String polyNm;
    private String rsdnRgstNmbr;
    private String type;
    private String mblNo;
    private String isMember;
    private String userPassword;

    private MultipartFile[] files;
}
