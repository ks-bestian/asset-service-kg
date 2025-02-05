package kr.co.bestiansoft.ebillservicekg.admin.acsHist.service;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;

import java.util.HashMap;
import java.util.List;

public interface AcsHistService {
    List<AcsHistVo> getAcsHistList(HashMap<String, Object> param);
    void createAcsHist(AcsHistVo acsHistVo);
    void createEbsAcsHist(AcsHistVo acsHistVo);
}
