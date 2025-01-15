package kr.co.bestiansoft.ebillservicekg.admin.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {

    private String deptCd;
    private String email;
    private String genCd;
    private String profileImgPath;
    private String jobCd;
    private String msgRcvYn;
    private String posCd;
    private String userId;
    private String userNm;
    private String userNmKg;
    private String userNmRu;
    private String deptHeadYn;
    private String docMgrYn;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
    private String deptNm;
    private String jobNm;
}
