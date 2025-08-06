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
    private String bzentyNm1;
    private String bzentyNm2;
    private String bzentyNm3;

    //제품구분
    private String eqpmntSeNm;
    private String eqpmntSeNm1;
    private String eqpmntSeNm2;
    private String eqpmntSeNm3;

    private Boolean fileExist;
    private String videoMnlId;
    private String fileNm;
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
        this.bzentyNm1 = vo.getBzentyNm1();
        this.bzentyNm2 = vo.getBzentyNm2();
        this.bzentyNm3 = vo.getBzentyNm3();
        this.eqpmntSeNm = vo.getEqpmntSeNm();
        this.eqpmntSeNm1 = vo.getEqpmntSeNm1();
        this.eqpmntSeNm2 = vo.getEqpmntSeNm2();
        this.eqpmntSeNm3 = vo.getEqpmntSeNm3();
    }
}
