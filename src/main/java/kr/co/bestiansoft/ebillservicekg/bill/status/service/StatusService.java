package kr.co.bestiansoft.ebillservicekg.bill.status.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.status.vo.StatusVo;

public interface StatusService {

	List<StatusVo> getMtngList(HashMap<String, Object> param);

	List<StatusVo> getMonitorList(HashMap<String, Object> param);

}
