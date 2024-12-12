package kr.co.bestiansoft.ebillservicekg.admin.member.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberVo extends ComDefaultVO {
    private String memberId;
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
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
