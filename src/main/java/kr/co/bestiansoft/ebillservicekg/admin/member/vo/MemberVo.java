package kr.co.bestiansoft.ebillservicekg.admin.member.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MemberVo extends ComDefaultVO {
    private String memberId;
    private String memberNm;
    private String memberNmKg;
    private String memberNmRu;
    private String ageCd;
    private String cmitCd;
    private String deptId;
    private String polyCd;
    private String polyNm;
    private String rsdnRgstNmbr;
    private String email;
    private String genCd;
    private String profileImgPath;
    private String mblNo;
    private String msgRcvYn;
    private String docMgrYn;
    private String deptNm;
    private String userPassword;
    private List<String> cmitList;
}
