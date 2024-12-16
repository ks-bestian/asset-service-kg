package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;

public interface BillMngService {

    List<BillMngVo> getBillList(HashMap<String, Object> param);
    BillMngVo getBillById(String billId);
    BillMngVo createBill(BillMngVo billMngVo);

}
