package kr.co.bestiansoft.ebillservicekg.admin.lngCode.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;

public interface LngCodeService {
    List<LngCodeVo> getLngCodeList(HashMap<String, Object> param);
    LngCodeVo createLngCode(LngCodeVo lngCodeVo);
    int updateLngCode(LngCodeVo lngCodeVo);
    void deleteLngCode(List<Long> lngId);
    LngCodeVo getLngCodeById(Long lngId);
}
