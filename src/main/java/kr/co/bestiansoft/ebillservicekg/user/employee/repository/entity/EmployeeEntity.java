package kr.co.bestiansoft.ebillservicekg.user.employee.repository.entity;

import kr.co.bestiansoft.ebillservicekg.user.Role;
import kr.co.bestiansoft.ebillservicekg.user.employee.domain.Employee;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_user")
@Entity
public class EmployeeEntity {

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	@Column(name = "user_id")
	private String userId;

    private String userNm;
    private String pswd;
    private String email;
    private String mblNo;
    private String inrNo;
    private String initDt;
    private String retiDt;
    private Timestamp regDt;
    private Timestamp modDt;
    private String statCd;
    private String posCd;
    private String jobCd;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "dept_cd")
    private String deptId;
    private String genCd;
    private String imgPath;
    private String msgRcvYn;

    @Builder
    public EmployeeEntity(String userId, String userNm, String pswd, String email
    				, String mblNo, String inrNo, String address, String fax
    				, String statCd, String posCd, String jobCd, Role role, String deptId
    				, Timestamp regDt, Timestamp modDt, String initDt, String retiDt, String genCd) {
        this.userId = userId;
        this.userNm = userNm;
        this.pswd = pswd;
        this.email = email;
        this.mblNo = mblNo;
        this.inrNo = inrNo;
        this.statCd = statCd;
        this.posCd = posCd;
        this.jobCd = jobCd;
        this.role = role;
        this.deptId = deptId;
        this.regDt = regDt;
        this.modDt = modDt;
        this.initDt = initDt;
        this.retiDt = retiDt;
        this.genCd = genCd;
    }

    public static EmployeeEntity from(Employee user) {
        return EmployeeEntity.builder()
                         .userId(user.getUserId())
                         .userNm(user.getUserNm())
                         .pswd(user.getPswd())
                         .email(user.getEmail())
                         .mblNo(user.getMblNo())
                         .inrNo(user.getInrNo())
                         .statCd(user.getStatCd())
                         .initDt(user.getInitDt())
                         .retiDt(user.getRetiDt())
                         .role(user.getRole())
                         .genCd(user.getGenCd())
                         .regDt(user.getRegDt())
                         .deptId(user.getDeptId())
                         .build();
    }

    public static EmployeeEntity from(Employee user, String deptId) {
        return EmployeeEntity.builder()
                         .userId(user.getUserId())
                         .userNm(user.getUserNm())
                         .pswd(user.getPswd())
                         .email(user.getEmail())
                         .mblNo(user.getMblNo())
                         .inrNo(user.getInrNo())
                         .statCd(user.getStatCd())
                         .initDt(user.getInitDt())
                         .retiDt(user.getRetiDt())
                         .role(user.getRole())
                         .genCd(user.getGenCd())
                         .deptId(deptId)
                         .regDt(user.getRegDt())
                         .build();
    }

    public Employee toModel() {
        return Employee.builder()
                   .userId(userId)
                   .userNm(userNm)
                   .pswd(pswd)
                   .email(email)
                   .mblNo(mblNo)
                   .inrNo(inrNo)
                   .statCd(statCd)
                   .role(role)
                   .initDt(initDt)
                   .retiDt(retiDt)
                   .regDt(regDt)
                   .modDt(modDt)
                   .jobCd(jobCd)
                   .posCd(posCd)
                   .genCd(genCd)
                   .deptId(deptId)
                   .build();
    }

    public EmployeeEntity update(Employee updateUser, String deptId) {
        this.userNm = updateUser.getUserNm() != null ? updateUser.getUserNm() : this.userNm;
        this.pswd = updateUser.getPswd() != null ? updateUser.getPswd() : this.pswd;
        this.email = updateUser.getEmail() != null ? updateUser.getEmail() : this.email;
        this.mblNo = updateUser.getMblNo() != null ? updateUser.getMblNo() : this.mblNo;
        this.inrNo = updateUser.getInrNo() != null ? updateUser.getInrNo() : this.inrNo;
        this.statCd = updateUser.getStatCd() != this.statCd ? updateUser.getStatCd() : this.statCd;
        this.posCd = updateUser.getPosCd() != null ? updateUser.getPosCd() : this.posCd;
        this.jobCd = updateUser.getJobCd() != null ? updateUser.getJobCd() : this.jobCd;
        this.role = updateUser.getRole() != null ? updateUser.getRole() : this.role;
        this.deptId = deptId != null ? deptId : this.deptId;

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
