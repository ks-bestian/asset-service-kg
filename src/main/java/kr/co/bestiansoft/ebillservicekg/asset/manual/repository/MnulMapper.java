package kr.co.bestiansoft.ebillservicekg.asset.manual.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;

@Mapper
public interface MnulMapper {
    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);
    List<MnulVo> getMnulListByEqpmntId(String eqpmntId);
    int createMnul(List<MnulVo> mnulVoList);
    void deleteMnul(String eqpmntId);
    void deleteMnulById(String mnlId);

}
