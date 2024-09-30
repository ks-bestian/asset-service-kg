package kr.co.bestiansoft.ebillservicekg.user.login.domain;

import kr.co.bestiansoft.ebillservicekg.user.department.repository.entity.DepartmentEntity;
import kr.co.bestiansoft.ebillservicekg.user.employee.repository.entity.EmployeeEntity;
import kr.co.bestiansoft.ebillservicekg.user.member.repository.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class Account extends User {
	
	/*
		0. 계정 아이디, 패스워드, 계정 유형(user/member), memberEntity, userEntity
		1. 부서를 하나만 선택 한다는 가정 하에 도메인을 구성한다.
		2. 아이디, 부서코드로 부서정보, 권한정보 조회. 기본 디폴트는 직원테이블의 부서코드. 겸직 부서 선택하면 겸직부서 테이블에서 데이터 조인해서 가져오게함.
		4. 국회의원은 겸직은 따로 없고, 의원실 코드가 부서 코드가 될것임. 권한 테이블에 국회의원 권한을 추가 하여 매핑해주면 될건데 권한은 나중에 연결하면 되지 않을까.
		3. 권한은 나중에... 
	
	 */
	
    private String accountId;
    private String pswd;
    
    //직원인지 국회의원인지(user/member)
    private String accountType;
    
    // 부서아이디
    private String deptCd;
    // 부서명1
    private String deptNm1;
    // 부서명
    private String deptNm2;
    // 부서약칭
    private String shrtNm;
    // 상위부서아이디
    private String uprDeptId;
    
    private MemberEntity member;
    private EmployeeEntity employee;
    
    // 패스워드를 지우는 메서드
    public void clearPassword() {
        this.pswd = null;
        if (this.member!=null) {
        	this.member.clearPassword();
        }else {
        	this.employee.clearPassword();
        }
    }

    public Account(EmployeeEntity employee, DepartmentEntity dept, List<GrantedAuthority> grantedAuthorities) {

        super(employee.getUserId(), employee.getPswd(), grantedAuthorities);

        if (employee !=null) {
            this.accountType = "user";
            this.accountId = employee.getUserId();
            this.pswd = employee.getPswd();
            this.employee = employee;
        }

        if (dept!=null) {
            this.deptCd = dept.getDeptId();
            this.deptNm1 = dept.getDeptNm1();
            this.deptNm2 = dept.getDeptNm2();
            this.shrtNm = dept.getShrtNm();
            this.uprDeptId = dept.getUprDeptId();
        }

    }

    public Account(MemberEntity member, DepartmentEntity dept, List<GrantedAuthority> grantedAuthorities) {

        super( member.getMemberId(), member.getPswd(), grantedAuthorities);

        if (member!=null) {
            this.accountType = "member";
            this.accountId = member.getMemberId();
            this.pswd = member.getPswd();
            this.member = member;
        }

        if (dept!=null) {
            this.deptCd = dept.getDeptId();
            this.deptNm1 = dept.getDeptNm1();
            this.deptNm2 = dept.getDeptNm2();
            this.shrtNm = dept.getShrtNm();
            this.uprDeptId = dept.getUprDeptId();
        }


    }
}
