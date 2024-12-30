package kr.co.bestiansoft.ebillservicekg.login.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class Account extends User {

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
	
	public Account(LoginUserVo user, List<GrantedAuthority> grantedAuthorities) {
		super(user.getUserId(), user.getPassword(), grantedAuthorities);
		
		this.userId = user.getUserId();
		this.userNmKg = user.getUserNmKg();
		this.userNmRu = user.getUserNmRu();
		this.memberId = user.getMemberId();
		this.memberNmKg = user.getMemberNmKg();
		this.memberNmRu = user.getMemberNmRu();
		this.deptCd = user.getDeptCd();
		this.email = user.getEmail();
		this.genCd = user.getGenCd();
		this.profileImgPath = user.getProfileImgPath();
		this.msgRcvYn = user.getMsgRcvYn();
		this.docMgrYn = user.getDocMgrYn();
		this.deptHeadYn = user.getDeptHeadYn();
		this.jobCd = user.getJobCd();
		this.posCd = user.getPosCd();
		this.ageCd = user.getAgeCd();
		this.cmitCd = user.getCmitCd();
		this.polyCd = user.getPolyCd();
		this.polyNm = user.getPolyNm();
		this.rsdnRgstNmbr = user.getRsdnRgstNmbr();
	}
	
}
