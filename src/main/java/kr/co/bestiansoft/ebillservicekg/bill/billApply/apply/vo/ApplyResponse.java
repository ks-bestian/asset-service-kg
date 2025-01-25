package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.Data;

@Data
public class ApplyResponse {

	ApplyVo applyDetail;

	List<ApplyVo> applyList;

	List<AgreeVo> proposerList;

	List<EbsFileVo> fileList;

	ProcessVo processVo;
}
