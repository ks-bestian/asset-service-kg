package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.util.List;

import lombok.Data;

@Data
public class BillMngResponse {

	private List<ProposerVo> proposerList;
    private List<BillMngVo> cmtList;
    private BillMngVo billMngVo;



}
