package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import lombok.Data;

@Data
public class ApplyResponse {
	
	ApplyVo applyVo;
	
	List<ApplyVo> applyList;
	
//	todo :: memberVo 필요
//	List<MemberVo> proposerList; 
}
