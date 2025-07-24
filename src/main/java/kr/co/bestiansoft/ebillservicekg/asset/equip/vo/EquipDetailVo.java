package kr.co.bestiansoft.ebillservicekg.asset.equip.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EquipDetailVo {
    //장비
    private String eqpmntId;
    private String eqpmntCd;
    private String eqpmntSe;
    private String eqpmntNm;
    private String expln;
    private String bzentyId;
    private String tkcgDeptId;

    //설치위치
    private String instlId;
    private String instlYmd;
    private String dscdYmd;
    private String instlPlcCd;
    private String instlPlcDtl1;
    private String instlPlcDtl2;
    private String instlPlcDtl3;
    private String rmrk;
    private String instlPicNm;

    //업체
    private String bzentyNm;

    //제품구분
    private String eqpmntSeNm;

    @Builder
    public EquipDetailVo(EquipDetailVo vo) {
        this.eqpmntId = vo.getEqpmntId();
        this.eqpmntCd = vo.getEqpmntCd();
        this.eqpmntSe = vo.getEqpmntSe();
        this.eqpmntNm = vo.getEqpmntNm();
        this.expln = vo.getExpln();
        this.bzentyId = vo.getBzentyId();
        this.tkcgDeptId = vo.getTkcgDeptId();
        this.bzentyNm = vo.getBzentyNm();
        this.eqpmntSeNm = vo.getEqpmntSeNm();
    }
}
