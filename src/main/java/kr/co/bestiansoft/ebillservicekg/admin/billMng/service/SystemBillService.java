package kr.co.bestiansoft.ebillservicekg.admin.billMng.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillResponse;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface SystemBillService {

	SystemBillResponse selectBillDetail(String billId, HashMap<String, Object> param);

	SystemBillVo createBillDetail(SystemBillVo systemBillVo);

	List<EbsFileVo> selectOpinionFile(String billId);

	SystemBillVo createBillFile(SystemBillVo systemBillVo);

	SystemBillVo updateBillLegal(SystemBillVo systemBillVo);

	SystemBillResponse selectBillMtng(String billId, HashMap<String, Object> param);

	SystemBillVo createMtngFile(SystemBillVo systemBillVo);

	SystemBillVo createValidationDept(SystemBillVo systemBillVo);

	SystemBillVo updateFileRmk(SystemBillVo systemBillVo);

	SystemBillVo createMasterCmt(SystemBillVo systemBillVo);

	SystemBillResponse selectBillMtnList(String billId, HashMap<String, Object> param);

	SystemBillVo createMtnMaster(SystemBillVo systemBillVo);

	SystemBillResponse selectBillRelationMtngList(String billId, HashMap<String, Object> param);

	SystemBillVo cretaeRelateMtng(SystemBillVo systemBillVo);

	SystemBillResponse selectBillGoverment(String billId, HashMap<String, Object> param);

	SystemBillVo cretaeGoverment(SystemBillVo systemBillVo);

	SystemBillResponse getApplyDetail(String billId, String lang);

}
