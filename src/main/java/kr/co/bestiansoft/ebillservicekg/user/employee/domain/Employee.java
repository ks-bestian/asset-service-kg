package kr.co.bestiansoft.ebillservicekg.user.employee.domain;

import kr.co.bestiansoft.ebillservicekg.user.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@ToString
@Data
public class Employee {

    // 사용자아아디
    private String userId;
    // 사용자이름
    private String userNm;
    // 비밀번호
    private String pswd;
    // 부서코드
    private String deptId;
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
    // 수정일시
    private Timestamp modDt;
    // 직위코드
    private String posCd;
    // 직급코드
    private String jobCd;
    // 권한
    private Role role;
    // 성별
    private String genCd;

    @Builder
    public Employee(String userId, String userNm, String pswd, String email, String inrNo, String mblNo,
                    String statCd, String posCd, String jobCd, Role role, Timestamp regDt, Timestamp modDt,
                    String initDt, String retiDt, String genCd, String deptId) {
        this.userId = userId;
        this.userNm = userNm;
        this.pswd = pswd;
        this.email = email;
        this.inrNo = inrNo;
        this.mblNo = mblNo;
        this.statCd = statCd;
        this.posCd = posCd;
        this.jobCd = jobCd;
        this.role = role;
        this.regDt = regDt;
        this.modDt = modDt;
        this.initDt = initDt;
        this.retiDt = retiDt;
        this.genCd = genCd;
        this.deptId = deptId;
    }

    @Builder
    public Employee(String userId, String username, String password, String email, String phoneNumber
    			, String telNumber, String address, String fax, String statCd, String position
    			, String rank, Role role, String posCd, String jobCd, String genCd) {
        this.userId = userId;
        this.userNm = username;
        this.pswd = password;
        this.email = email;
        this.mblNo = phoneNumber;
        this.inrNo = telNumber;
        this.statCd = statCd;
        this.posCd = posCd;
        this.jobCd = jobCd;
        this.role = role;
        this.genCd = genCd;
    }

    private static Employee userRole(Employee user, String role) {
        if (Role.ADMIN.name().equals(role)) {
            user.role = Role.ADMIN;
        } else if (Role.ASSEMBLY_MEMBER.name().equals(role)) {
            user.role = Role.ASSEMBLY_MEMBER;
        } else {
            user.role = Role.USER;
        }

        return user;
    }

}
