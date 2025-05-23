package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;

public interface AgreeService {

	List<ApplyVo> getAgreeList(HashMap<String, Object> param);

	AgreeResponse getAgreeDetail(String billId, String lang);

	int setBillAgree(String billId, HashMap<String, Object> param);

}
