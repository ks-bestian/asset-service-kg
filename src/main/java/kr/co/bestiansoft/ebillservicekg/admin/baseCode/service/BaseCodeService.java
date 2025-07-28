package kr.co.bestiansoft.ebillservicekg.admin.baseCode.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;

public interface BaseCodeService {
    List<BaseCodeVo> getBaseCodeList(HashMap<String, Object> param);
    BaseCodeVo createBaseCode(BaseCodeVo baseCodeVo);
    int updateBaseCode(BaseCodeVo baseCodeVo);
    void deleteBaseCode(List<String> ids);
    BaseCodeVo getBaseCodeById(Long baseCode);
}
