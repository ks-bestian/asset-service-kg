package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;

public interface BillMngService {
	/* 안건 관리 리스트 화면 */
    List<BillMngVo> getBillList(HashMap<String, Object> param);
    
    /* 안건 관리 상세화면 - 안건 상세 정보, 위원회 정보, 발의자 정보 등*/
    BillMngResponse getBillById(HashMap<String, Object> param);
    
    /* 안건관리 - 서면 등록 화면 */
    BillMngVo createBill(BillMngVo billMngVo);

}
