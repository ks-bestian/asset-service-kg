package kr.co.bestiansoft.ebillservicekg.asset.bzenty.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;

public interface BzentyService {
    List<BzentyVo> getBzentyList(HashMap<String, Object> param);
    BzentyVo getBzentyDetail(String bzentyId);
    BzentyVo createBzenty(BzentyVo EqpmntVo);
    int updateBzenty(BzentyVo EqpmntVo);
    void deleteBzenty(List<String> seq);
}
