package kr.co.bestiansoft.ebillservicekg.asset.manual.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;

public interface MnulService {
    int createMnul(List<MnulVo> mnulVo, String eqpmntId);

    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);

    List<MnulVo> getMnulListByEqpmntId(String eqpmntId);

    void deleteMnul(String eqpmntId);
    void deleteMnulById(List<String> ids);

}
