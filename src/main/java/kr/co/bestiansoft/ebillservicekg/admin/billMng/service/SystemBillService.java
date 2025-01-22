package kr.co.bestiansoft.ebillservicekg.admin.billMng.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface SystemBillService {

	SystemBillVo selectBillDetail(String billId, String lang);

	SystemBillVo createBillDetail(SystemBillVo systemBillVo);

	List<EbsFileVo> selectOpinionFile(String billId);

	SystemBillVo createBillFile(SystemBillVo systemBillVo);

}
