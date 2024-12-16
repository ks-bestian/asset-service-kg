package kr.co.bestiansoft.ebillservicekg.admin.lawInfo.service;

import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.vo.LawInfoVo;
import java.util.HashMap;
import java.util.List;

public interface LawInfoService {
    List<LawInfoVo> getLawInfoList(HashMap<String, Object> param);
    LawInfoVo getLawInfoDetail(Long lawId);
    LawInfoVo createLawInfo(LawInfoVo lawInfoVo);
    int updateLawInfo(LawInfoVo lawInfoVo);
    void deleteLawInfo(List<Long> ids);
}
