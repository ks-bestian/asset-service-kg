package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import lombok.Data;

@Data
public class ApplyResponse {
	
	ApplyVo applyDetail;
	
	List<ApplyVo> applyList;
	
	List<AgreeVo> proposerList;
}
