package kr.co.bestiansoft.ebillservicekg.admin.acsHist.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;

public interface AcsHistService {
    List<AcsHistVo> getAcsHistList(HashMap<String, Object> param);
    List<AcsHistVo> getBillHistList(HashMap<String, Object> param);
    void createAcsHist(AcsHistVo acsHistVo);
    void createBillHist(AcsHistVo acsHistVo);
}
