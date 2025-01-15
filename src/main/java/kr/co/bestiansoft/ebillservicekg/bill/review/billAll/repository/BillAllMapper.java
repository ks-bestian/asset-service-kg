package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;

@Mapper
public interface BillAllMapper {
    List<BillAllVo> selectBillList (HashMap<String, Object> param);
    BillAllVo getBillById(String id);
}
