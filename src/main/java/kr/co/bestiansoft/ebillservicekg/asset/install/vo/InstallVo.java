package kr.co.bestiansoft.ebillservicekg.asset.install.vo;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InstallVo extends ComDefaultVO {
    private String instlId;
    private String eqpmntId;
    private String instlYmd;
    private String dscdYmd;
    private String instlPlcCd;
    private String instlPlcDtl1;
    private String instlPlcDtl2;
    private String instlPlcDtl3;
    private String rmrk;
    private String instlPicNm;
    
    //설치장소 이미지
    private String imgId;
    //설치 위치명
    private String cdNm;
    private String instlPlcNm1;
    private String instlPlcNm2;
    private String instlPlcNm3;

    public InstallVo(EquipDetailVo vo) {
        this.instlId = vo.getInstlId();
        this.eqpmntId = vo.getEqpmntId();
        this.instlYmd = vo.getInstlYmd();
        this.dscdYmd = vo.getDscdYmd();
        this.instlPlcCd = vo.getInstlPlcCd();
        this.instlPlcDtl1 = vo.getInstlPlcDtl1();
        this.instlPlcDtl2 = vo.getInstlPlcDtl2();
        this.instlPlcDtl3 = vo.getInstlPlcDtl3();
        this.rmrk = vo.getRmrk();
        this.instlPicNm = vo.getInstlPicNm();
    }

}
