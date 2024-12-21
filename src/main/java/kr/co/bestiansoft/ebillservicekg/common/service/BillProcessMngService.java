package kr.co.bestiansoft.ebillservicekg.common.service;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillProcMngVo;

public interface BillProcessMngService {

	BillProcMngVo makeProc(String procKingCd,String billId);

}
