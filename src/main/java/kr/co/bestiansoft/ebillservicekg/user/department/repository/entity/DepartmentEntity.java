package kr.co.bestiansoft.ebillservicekg.user.department.repository.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_dept")
@Entity
public class DepartmentEntity {

    @Column(name = "dept_cd")
    @Id
    // 부서아이디
    private String deptId;
    // 부서명1
    private String deptNm1;
    // 부서명2
    private String deptNm2;
    // 부서약칭
    private String shrtNm;
    // 순서
    private Integer ord;
    // 상위부서아이디
    private String uprDeptId;
    // 카테고리아이디
    private String ctgrId;
    // 사용여부
    private String useYn;
    // 부서신설일
    private String bgDt;
    // 부서폐쇄일
    private String edDt;
    // 등록일시
    private LocalDateTime regDt;
    // 등록자아이디
    private String regId;
    // 수정일시
    private LocalDateTime modDt;
    // 수정자아이디
    private String modId;
    // 담당자아이디
    private String mngrId;


    @Builder
    public DepartmentEntity(String deptId, String deptNm1, String deptNm2, String shrtNm, Integer ord, String uprDeptId, String ctgrId, String useYn, String bgDt, String edDt, LocalDateTime regDt, String regId, LocalDateTime modDt, String modId, String mngrId) {
        this.deptId = deptId;
        this.deptNm1 = deptNm1;
        this.deptNm2 = deptNm2;
        this.shrtNm = shrtNm;
        this.ord = ord;
        this.uprDeptId = uprDeptId;
        this.ctgrId = ctgrId;
        this.useYn = useYn;
        this.bgDt = bgDt;
        this.edDt = edDt;
        this.regDt = regDt;
        this.regId = regId;
        this.modDt = modDt;
        this.modId = modId;
        this.mngrId = mngrId;
    }
}
