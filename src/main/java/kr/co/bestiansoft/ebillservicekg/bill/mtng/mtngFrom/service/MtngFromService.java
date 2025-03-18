package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;

public interface MtngFromService {

    List<MtngFromVo> getMtngFromList(HashMap<String, Object> param);
    MtngFromVo getMtngFromById(Long mtngId, HashMap<String, Object> param);
    MtngFromVo createMtngFrom(MtngFromVo mtngFromVo);
    List<MemberVo> getMemberList(HashMap<String, Object> param);
    List<MemberVo> getDeptList(HashMap<String, Object> param);
    void deleteMtng(List<Long> mtngIds);

    List<BillMngVo> selectListMtngBill(HashMap<String, Object> param);
    List<BillMngVo> selectListMainMtngBill(HashMap<String, Object> param);
	MtngFromVo updateMtngBill(MtngFromVo mtngFromVo);
	
	List<MtngFromVo> selectListMtngByBillId(String billId);
}
