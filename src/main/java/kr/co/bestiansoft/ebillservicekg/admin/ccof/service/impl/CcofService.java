package kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;

import java.util.HashMap;
import java.util.List;

public interface CcofService {
    List<CcofVo> getCcofList(HashMap<String, Object> param, String userId);
}
