package kr.co.bestiansoft.ebillservicekg.asset.manual.repository;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MnulMapper {
    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);
    List<MnulVo> getMnulListByEqpmntId(HashMap<String, String> params);
    MnulVo getVideoByMnlId(String mnlId);
    int createMnul(List<MnulVo> mnulVoList);
    void deleteMnul(String eqpmntId);
    void deleteMnulById(String mnlId);
    int upsertMnul(MnulVo vo);

}
