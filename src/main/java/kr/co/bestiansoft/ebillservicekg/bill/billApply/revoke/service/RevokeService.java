package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;

public interface RevokeService {

	List<ApplyVo> getRevokeList(HashMap<String, Object> param);

	RevokeResponse getRevokeDetail(String billId, String lang);

	ProcessVo billRevokeRequest(String billId,RevokeVo vo);
	
	ProcessVo billRevokeSubmit(String billId,RevokeVo vo);

	int billRevokeCancel(String billId, HashMap<String, Object> param);

	int updateRevoke(String billId, RevokeVo revokeVo);

//	boolean hasEveryProposerAgreedToRevoke(String billId);

}
