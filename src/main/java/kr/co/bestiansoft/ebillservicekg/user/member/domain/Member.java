package kr.co.bestiansoft.ebillservicekg.user.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.sql.Timestamp;

@ToString
@Getter
public class Member {

	private Long id;
	// 의원아이디
	private String memberId;
	// 의원명
	private String memberNm;
	// 비밀번호
	private String pswd;
	// 정당코드
	private String polyCd;
	// 정당명
	private String polyNm;
	// 대별코드
	private String ageCd;
	// 부서코드
	private String deptId;
	// 소속위원회코드
	private String cmitCd;
	// 상태코드
	private String statCd;
	// 이메일
	private String email;
	// 모바일번호
	private String mblNo;
	// 내선번호
	private String inrNo;
	// 임용일
	private String initDt;
	// 퇴직일
	private String retiDt;
	// 등록일시
	private Timestamp regDt;
	// 등록자아이디
	private String regId;
	// 수정일시
	private Timestamp modDt;
	// 수정자아이디
	private String modId;
 	// 성별
 	private String genCd;
 	// 의원아이디
 	private String memberNo;
	
	@Builder
	public Member(Long id, String memberId, String memberNm, String pswd, String polyCd, String polyNm, String ageCd,
                  String deptId, String cmitCd, String statCd, String email, String mblNo, String inrNo, String initDt,
                  String retiDt, Timestamp regDt, String regId, Timestamp modDt, String modId, String genCd, String memberNo) {
		this.id = id;
		this.memberId = memberId;
		this.memberNm = memberNm;
		this.pswd = pswd;
		this.polyCd = polyCd;
		this.polyNm = polyNm;
		this.ageCd = ageCd;
		this.deptId = deptId;
		this.cmitCd = cmitCd;
		this.statCd = statCd;
		this.email = email;
		this.mblNo = mblNo;
		this.inrNo = inrNo;
		this.initDt = initDt;
		this.retiDt = retiDt;
		this.regDt = regDt;
		this.regId = regId;
		this.modDt = modDt;
		this.modId = modId;
		this.genCd = genCd;
		this.memberNo = memberNo;
	}


}
