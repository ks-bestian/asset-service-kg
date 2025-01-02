package kr.co.bestiansoft.ebillservicekg.login.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class LoginUserVo extends ComDefaultVO {

	private String userId;
	private String userNmKg;
	private String userNmRu;
	private String memberId;
	private String memberNmKg;
	private String memberNmRu;
	private String deptCd;
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
	private String polyCd;
	private String polyNm;
	private String rsdnRgstNmbr;
	
	private String password = "best1234"; //임시 비밀번호
	
}
