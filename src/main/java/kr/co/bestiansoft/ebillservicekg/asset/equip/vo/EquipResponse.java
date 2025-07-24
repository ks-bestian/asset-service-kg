package kr.co.bestiansoft.ebillservicekg.asset.equip.vo;


import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import lombok.*;

import java.util.List;

@Data
public class EquipResponse {
    EquipDetailVo equipDetailVo;
    List<InstallVo> installList;
    List<MnulVo> mnulList;
    List<AmsImgVo> amsImgList;
}
