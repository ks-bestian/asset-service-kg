package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo;

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
    private String cmitNm;
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


    private String deptCd;
    private String deptNm;
    private String uprDeptCd;


    /* 회의관련 */
    private Long mtngId;
    private String atdtUserId;
    private String atdtUserNm;
    private String atdtKind;
    private String atdtDivCd;
    private String atdtDivNm;
    private String atdtDeptNm;
    
    private String atdtPosition;


}
