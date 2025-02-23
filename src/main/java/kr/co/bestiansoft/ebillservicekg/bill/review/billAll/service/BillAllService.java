package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;

public interface BillAllService {

    List<BillAllVo> getBillList(HashMap<String, Object> param);
    BillAllResponse getBillById(String billId, HashMap<String, Object> param);



}
