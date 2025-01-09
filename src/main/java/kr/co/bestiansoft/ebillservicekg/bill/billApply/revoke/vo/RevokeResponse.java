package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo;

import java.util.List;

import lombok.Data;

@Data
public class RevokeResponse {

	RevokeVo revokeDetail;
	
	List<RevokeVo> proposerList;
}
