package kr.co.bestiansoft.ebillservicekg.admin.baseCode.service;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;

import java.util.HashMap;
import java.util.List;

public interface BaseCodeService {
    List<BaseCodeVo> getBaseCodeList(HashMap<String, Object> param);
    BoardVo createBaseCode(BaseCodeVo baseCodeVo);
    int updateBaseCode(BaseCodeVo baseCodeVo);
    void deleteBaseCode(List<Long> ids);
    BoardVo getBaseCodeById(Long baseCode);
}
