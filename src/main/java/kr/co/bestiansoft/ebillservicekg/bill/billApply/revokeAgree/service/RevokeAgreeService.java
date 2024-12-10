package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo.RevokeAgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo.RevokeAgreeVo;

public interface RevokeAgreeService {

	List<RevokeAgreeVo> getRevokeAgreeList(HashMap<String, Object> param);

	RevokeAgreeResponse getRevokeDetail(String billId, HashMap<String, Object> param);

	int updateRevokeAgree(String billId, HashMap<String, Object> param);

}
