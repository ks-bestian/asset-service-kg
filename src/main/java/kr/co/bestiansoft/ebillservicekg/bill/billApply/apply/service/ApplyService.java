package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;

public interface ApplyService {

	ApplyVo createApply(ApplyVo applyVo) throws Exception;

	ApplyVo createApplyRegister(ApplyVo applyVo) throws Exception;

	List<ApplyVo> getApplyList(HashMap<String, Object> param);

	int updateApply(ApplyVo applyVo, String billId) throws Exception;

	int deleteApply(String billId);

	ApplyResponse getApplyDetail(String billId, HashMap<String, Object> param);

	int applyBill(String billId);

	int revokeBill(String billId, ApplyVo applyVo);

	int updateBillStatus(String billId, ApplyVo applyVo);

	ApplyVo saveBillAccept(String billId, ApplyVo applyVo);

	int deleteBillFile(EbsFileVo ebsFileVo);

	int updateFileOpbYn(EbsFileVo ebsFileVo);

	List<ApplyVo> selectBillAll(HashMap<String, Object> param);

	ApplyVo createBillHome(ApplyVo applyVo);

	void stopBillHome(ApplyVo applyVo);

	void createComments(CommentsVo commentsVo);
}
