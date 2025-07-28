package kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;

public interface CcofService {
    List<CcofVo> getCcofList(HashMap<String, Object> param);
}
