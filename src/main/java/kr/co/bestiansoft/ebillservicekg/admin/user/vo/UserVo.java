package kr.co.bestiansoft.ebillservicekg.admin.user.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class UserVo extends ComDefaultVO {

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
    private String deptNm;
    private String jobNm;
    private String ccofCd;
    private List<String> ccofCds;
    private Integer ord;
    private String userPassword;
}
