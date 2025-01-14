package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;

public interface RevokeService {

	List<RevokeVo> getRevokeList(HashMap<String, Object> param);

	RevokeResponse getRevokeDetail(String billId, String lang);

	int billRevokeRequest(String billId, HashMap<String, Object> param);

	int billRevokeCancle(String billId, HashMap<String, Object> param);

	int updateRevoke(String billId, RevokeVo revokeVo);

}
