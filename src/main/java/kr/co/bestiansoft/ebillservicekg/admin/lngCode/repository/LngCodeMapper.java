package kr.co.bestiansoft.ebillservicekg.admin.lngCode.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;

@Mapper
public interface LngCodeMapper {

    List<LngCodeVo> selectListLngCode(HashMap<String, Object> param);
    int insertLngCode(LngCodeVo lngCodeVo);
    int updateLngCode(LngCodeVo lngCodeVo);
    void deleteLngCode(Long lngId);
    LngCodeVo selectLngCode(Long lngId);

}
