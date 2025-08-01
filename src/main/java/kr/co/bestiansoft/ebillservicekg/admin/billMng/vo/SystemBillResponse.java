package kr.co.bestiansoft.ebillservicekg.admin.billMng.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.Data;

@Data
public class SystemBillResponse {

	List<EbsFileVo> fileList;

	SystemBillVo billDetail;

	List<SystemBillVo> mtngList;

	List<EbsFileVo> cmtFileList;

	List<SystemBillVo> billDetailList;

	List<AgreeVo> proposerList;
}
