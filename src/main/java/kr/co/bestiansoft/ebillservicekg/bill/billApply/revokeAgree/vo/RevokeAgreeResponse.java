package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo;

import java.util.List;

import lombok.Data;

@Data
public class RevokeAgreeResponse {

	RevokeAgreeVo revokeAgreeDetail;
	
	List<RevokeAgreeVo> proposerList;
}
