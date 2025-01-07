package kr.co.bestiansoft.ebillservicekg.admin.lngCode.repository;

import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface LngCodeMapper {

    List<LngCodeVo> selectListLngCode(HashMap<String, Object> param);
    int insertLngCode(LngCodeVo lngCodeVo);
    int updateLngCode(LngCodeVo lngCodeVo);
    void deleteLngCode(Long lngId);
    LngCodeVo selectLngCode(Long lngId);

}
