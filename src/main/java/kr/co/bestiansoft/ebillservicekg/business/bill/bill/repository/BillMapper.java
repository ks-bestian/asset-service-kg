package kr.co.bestiansoft.ebillservicekg.business.bill.bill.repository;

import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillList;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {

    List<BillList> selectBills(BillSearch search);
    Long selectBillCount(BillSearch search);
}
