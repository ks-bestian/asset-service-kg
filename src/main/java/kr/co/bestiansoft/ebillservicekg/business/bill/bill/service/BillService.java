package kr.co.bestiansoft.ebillservicekg.business.bill.bill.service;

import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillList;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillSearch;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;

public interface BillService {

    ListResponse<BillList> getBills(BillSearch search);
}
