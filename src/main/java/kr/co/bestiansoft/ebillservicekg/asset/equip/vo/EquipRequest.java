package kr.co.bestiansoft.ebillservicekg.asset.equip.vo;


import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.util.List;

@Data
public class EquipRequest extends ComDefaultVO {
    private String eqpmntId;
    private String eqpmntCd;
    private String eqpmntSe;
    private String eqpmntNm;
    private String expln;
    private String bzentyId;
    private String tkcgDeptId;

    private String thumbNailImg;
    private List<String> detailImg;

    //pdf 메뉴얼
    List<MnulVo> mnulVoList;

    //설치 위치 정보
    List<InstallVo> installVoList;

}
