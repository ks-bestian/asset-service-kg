package kr.co.bestiansoft.ebillservicekg.user.department.domain;

import kr.co.bestiansoft.ebillservicekg.user.department.repository.entity.DepartmentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class Department {

    private String deptId;
    private String deptNm1;
    private String deptNm2;
    private String shrtNm;
    private Integer ord;
    private String uprDeptId;
    private String ctgrId;
    private String useYn;
    private String bgDt;
    private String edDt;
    private LocalDateTime regDt;
    private String regId;
    private LocalDateTime modDt;
    private String modId;
    private Department parentDepartment;
    private String mngrId;


    @Builder
    public Department(String deptId, String deptNm1, String deptNm2, String shrtNm, Integer ord, String uprDeptId, String ctgrId, String useYn, String bgDt, String edDt, LocalDateTime regDt, String regId, LocalDateTime modDt, String modId, Department parentDepartment, String mngrId) {
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
        this.parentDepartment = parentDepartment;
        this.mngrId = mngrId;
    }

    public Department addParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
        return this;
    }

    public static Department fromEntity(DepartmentEntity entity) {
        return Department.builder()
                .deptId(entity.getDeptId())
                .deptNm1(entity.getDeptNm1())
                .deptNm2(entity.getDeptNm2())
                .shrtNm(entity.getShrtNm())
                .ord(entity.getOrd())
                .uprDeptId(entity.getUprDeptId())
                .ctgrId(entity.getCtgrId())
                .useYn(entity.getUseYn())
                .bgDt(entity.getBgDt())
                .edDt(entity.getEdDt())
                .regDt(entity.getRegDt())
                .regId(entity.getRegId())
                .modDt(entity.getModDt())
                .modId(entity.getModId())
                .mngrId(entity.getMngrId())
                .build();
    }

}
