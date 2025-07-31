package kr.co.bestiansoft.ebillservicekg.asset.manual.service;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface MnulService {
    int createMnul(List<MnulVo> mnulVo, String eqpmntId, String mnlSe);

    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);

    List<MnulVo> getMnulListByEqpmntId(String eqpmntId);

    void deleteMnul(String eqpmntId);
    void deleteMnulById(List<String> ids);
    int upsertMnul(List<MnulVo> mnulVoList, String eqpmntId);
    Resource loadVideoAsResource(String videoId)throws IOException;


}
