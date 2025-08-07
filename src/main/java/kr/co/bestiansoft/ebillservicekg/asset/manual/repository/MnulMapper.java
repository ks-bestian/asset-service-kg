package kr.co.bestiansoft.ebillservicekg.asset.manual.repository;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MnulMapper {
    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);
    List<MnulVo> getMnulListByEqpmntId(HashMap<String, String> params);
    MnulVo getVideoByMnlId(String mnlId);
    MnulVo getMnlByFileNm(String fileNm);
    int createMnul(List<MnulVo> mnulVoList);
    void deleteMnul(String eqpmntId);
    void deleteMnulById(String mnlId);
    void deleteNotIn(@Param("eqpmntId") String eqpmntId, @Param("mnlIdList") List<String> mnlIdList);
    void deleteNotInfileNm(@Param("eqpmntId") String eqpmntId, @Param("fileNmList") List<String> fileNmList);
    int upsertMnul(MnulVo vo);

}
