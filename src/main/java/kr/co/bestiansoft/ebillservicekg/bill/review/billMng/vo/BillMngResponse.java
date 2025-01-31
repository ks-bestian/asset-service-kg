package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.Data;

@Data
public class BillMngResponse {

	private List<ProposerVo> proposerList;
    private List<BillMngVo> cmtList;
    private BillMngVo billMngVo;
    private ProcessVo processVo;





}
