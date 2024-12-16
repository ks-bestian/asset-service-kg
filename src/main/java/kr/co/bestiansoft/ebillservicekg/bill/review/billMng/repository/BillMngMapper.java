package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;

@Mapper
public interface BillMngMapper {
    List<BillMngVo> getBillList (HashMap<String, Object> param);
    BillMngVo getBillById(String id);
    void insertBill(BillMngVo billMngVo);
}
