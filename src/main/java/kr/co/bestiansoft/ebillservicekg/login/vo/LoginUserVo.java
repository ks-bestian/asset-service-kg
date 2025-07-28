package kr.co.bestiansoft.ebillservicekg.login.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class LoginUserVo extends ComDefaultVO {

	private String userId;
	private String userNmKg;
	private String userNmRu;
	private String memberId;
	private String memberNmKg;
	private String memberNmRu;
	private String deptCd;
	private String deptNmKg;
	private String deptNmRu;
	private String deptNmKo;
	private String email;
	private String genCd;
	private String profileImgPath;
	private String msgRcvYn;
	private String docMgrYn;
	private String deptHeadYn;
	private String jobCd;
	private String posCd;
	private String ageCd;
	private String cmitCd;
	private String cmitNmKg;
	private String cmitNmRu;
	private String cmitNmKo;
	private String polyCd;
	private String polyNm;
	private String rsdnRgstNmbr;
	private String uprDeptCd;

	private String password = "best1234"; //Temporary password

}
