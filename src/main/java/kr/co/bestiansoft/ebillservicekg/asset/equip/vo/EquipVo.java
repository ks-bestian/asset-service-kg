package kr.co.bestiansoft.ebillservicekg.asset.equip.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class EquipVo extends ComDefaultVO {
    private String eqpmntId;
    private String eqpmntCd;
    private String eqpmntSe;
    private String eqpmntNm;
    private String expln;
    private String bzentyId;
    private String tkcgDeptId;

    public EquipVo(EquipDetailVo vo) {
        this.eqpmntId = vo.getEqpmntId();
        this.eqpmntCd = vo.getEqpmntCd();
        this.eqpmntSe = vo.getEqpmntSe();
        this.eqpmntNm = vo.getEqpmntNm();
        this.expln = vo.getExpln();
        this.bzentyId = vo.getBzentyId();
        this.tkcgDeptId = vo.getTkcgDeptId();
    }

}
