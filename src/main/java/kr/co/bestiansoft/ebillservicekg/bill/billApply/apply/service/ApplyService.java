package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface ApplyService {

	ApplyVo createApply(ApplyVo applyVo);

	List<ApplyVo> getApplyList(HashMap<String, Object> param);

	int updateApply(ApplyVo applyVo, String billId);

	int deleteApply(String billId);

	ApplyResponse getApplyDetail(String billId, String lang);

	int applyBill(String billId);

	int revokeBill(String billId, ApplyVo applyVo);

	int updateBillStatus(String billId, ApplyVo applyVo);

	ApplyVo saveBillAccept(String billId, ApplyVo applyVo);

	int deleteBillFile(EbsFileVo ebsFileVo);

}
