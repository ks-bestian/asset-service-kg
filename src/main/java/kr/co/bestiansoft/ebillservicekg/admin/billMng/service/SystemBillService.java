package kr.co.bestiansoft.ebillservicekg.admin.billMng.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillResponse;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface SystemBillService {

	SystemBillResponse selectBillDetail(String billId, HashMap<String, Object> param);

	SystemBillVo createBillDetail(SystemBillVo systemBillVo);
	
	SystemBillVo updateBillDetail(SystemBillVo systemBillVo);

	List<EbsFileVo> selectOpinionFile(String billId);

	SystemBillVo createBillFile(SystemBillVo systemBillVo);

	SystemBillVo createBillLegal(SystemBillVo systemBillVo);

	SystemBillVo updateBillLegal(SystemBillVo systemBillVo);

}
