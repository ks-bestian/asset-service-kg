package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;

public interface MtngAllService {

    List<MtngAllVo> getMtngList(HashMap<String, Object> param);
    MtngAllVo getMtngById(Long mtngId, HashMap<String, Object> param);


    List<MemberVo> getMtngParticipants(HashMap<String, Object> param);


}
