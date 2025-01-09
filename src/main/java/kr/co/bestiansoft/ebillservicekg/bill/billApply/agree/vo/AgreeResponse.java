package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo;

import java.util.List;

import lombok.Data;

@Data
public class AgreeResponse {
	
    List<AgreeVo> proposerList;
    
    AgreeVo agreeDetail;
}
