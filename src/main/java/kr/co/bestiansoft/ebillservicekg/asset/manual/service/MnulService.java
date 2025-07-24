package kr.co.bestiansoft.ebillservicekg.asset.manual.service;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;

import java.util.HashMap;
import java.util.List;

public interface MnulService {
    int createMnul(List<MnulVo> mnulVo, String eqpmntId);

    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);

    List<MnulVo> getMnulListByEqpmntId(String eqpmntId);

    void deleteMnul(String eqpmntId);
    void deleteMnulById(List<String> ids);

}
