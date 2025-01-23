package kr.co.bestiansoft.ebillservicekg.admin.dept.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeptVo extends ComDefaultVO {
    private String deptCd;
    private String deptNm1;
    private String deptNm2;
    private String deptNm3;
    private String deptNm;
    private String shrtNm1;
    private String shrtNm2;
    private String shrtNm3;
    private String shrtNm;
    private int ord;
    private String uprDeptCd;
    private String useYn;

    private List<DeptVo> children = new ArrayList<>();

    public void addChildren(DeptVo children){
        this. children.add(children);
    }
}
