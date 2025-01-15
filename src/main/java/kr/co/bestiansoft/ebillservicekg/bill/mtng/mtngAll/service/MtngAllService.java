package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;

public interface MtngAllService {

    List<MtngAllVo> getMtngList(HashMap<String, Object> param);
    MtngAllVo getMtngById(String mtngId);

}
