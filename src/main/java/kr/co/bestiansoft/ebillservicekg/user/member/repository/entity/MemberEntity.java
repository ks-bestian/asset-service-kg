package kr.co.bestiansoft.ebillservicekg.user.member.repository.entity;

import kr.co.bestiansoft.ebillservicekg.user.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_member")
@Entity
public class MemberEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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
 	// 이미지경로
 	private String imgPath;
 	// 메세지수신설정
 	private String msgRcvYn;
 	// 의원아이디
 	private String memberNo;
 	
    @Builder
	public MemberEntity(Long id, String memberId, String memberNm, String pswd, String polyCd, String polyNm,
                        String ageCd, String deptId, String cmitCd, String statCd, String email, String mblNo, String inrNo,
                        String initDt, String retiDt, Timestamp regDt, String regId, Timestamp modDt, String modId, String genCd,
                        String memberNo) {
    	
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

	public static MemberEntity from(Member member) {
		return MemberEntity.builder()
						   .id(member.getId())
						   .memberId(member.getMemberId())
						   .memberNm(member.getMemberNm())
						   .pswd(member.getPswd())
						   .polyCd(member.getPolyCd())
						   .polyNm(member.getPolyNm())
						   .ageCd(member.getAgeCd())
						   .deptId(member.getDeptId())
						   .cmitCd(member.getCmitCd())
						   .statCd(member.getStatCd())
						   .email(member.getEmail())
						   .mblNo(member.getMblNo())
						   .inrNo(member.getInrNo())
						   .initDt(member.getInitDt())
						   .retiDt(member.getRetiDt())
						   .regDt(member.getRegDt())
						   .regId(member.getRegId())
						   .modDt(member.getModDt())
						   .modId(member.getModId())
						   .genCd(member.getGenCd())
						   .memberNo(member.getMemberNo())
						   .build();
	}

	public Member toModel() {
		return Member.builder()
					 .id(id)
					 .memberId(memberId)
					 .memberNm(memberNm)
					 .pswd(pswd)
					 .polyCd(polyCd)
					 .polyNm(polyNm)
					 .ageCd(ageCd)
					 .deptId(deptId)
					 .cmitCd(cmitCd)
					 .statCd(statCd)
					 .email(email)
					 .mblNo(mblNo)
					 .inrNo(inrNo)
					 .initDt(initDt)
					 .retiDt(retiDt)
					 .regDt(regDt)
					 .regId(regId)
					 .modDt(modDt)
					 .modId(modId)
					 .genCd(genCd)
					 .memberNo(memberNo)
					 .build();
	}

	public MemberEntity update(Member updateMember) {
		this.memberNm = updateMember.getMemberNm() != null ? updateMember.getMemberNm() : this.memberNm;
		this.memberNo = updateMember.getMemberNo() != null ? updateMember.getMemberNo() : this.memberNo;
		this.polyCd = updateMember.getPolyCd() != null ? updateMember.getPolyCd() : this.polyCd;
		this.polyNm = updateMember.getPolyNm() != null ? updateMember.getPolyNm() : this.polyNm;
		this.ageCd = updateMember.getAgeCd() != null ? updateMember.getAgeCd() : this.ageCd;
		this.deptId = updateMember.getDeptId() != null ? updateMember.getDeptId() : this.deptId;
		this.cmitCd = updateMember.getCmitCd() != null ? updateMember.getCmitCd() : this.cmitCd;
		this.statCd = updateMember.getStatCd() != null ? updateMember.getStatCd() : this.statCd;
		this.email = updateMember.getEmail() != null ? updateMember.getEmail() : this.email;
		this.mblNo = updateMember.getMblNo() != null ? updateMember.getMblNo() : this.mblNo;
		this.inrNo = updateMember.getInrNo() != null ? updateMember.getInrNo() : this.inrNo;
		this.initDt = updateMember.getInitDt() != null ? updateMember.getInitDt() : this.initDt;
		this.retiDt = updateMember.getRetiDt() != null ? updateMember.getRetiDt() : this.retiDt;
		this.modDt = updateMember.getModDt();
		this.modId = updateMember.getModId();
		
		return this;
	}

	public void updateDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void nullDeptCd() {
		this.deptId = null;
	}
	
    // 패스워드를 지우는 메서드
    public void clearPassword() {
        this.pswd = null;
    }
	
}
