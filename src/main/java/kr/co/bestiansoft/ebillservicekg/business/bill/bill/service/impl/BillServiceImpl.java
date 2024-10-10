package kr.co.bestiansoft.ebillservicekg.business.bill.bill.service.impl;

import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillList;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.domain.BillSearch;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.repository.BillMapper;
import kr.co.bestiansoft.ebillservicekg.business.bill.bill.service.BillService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BillServiceImpl implements BillService {

    private final BillMapper billMapper;

    @Override
    public ListResponse<BillList> getBills(BillSearch search) {
        List<BillList> billList = billMapper.selectBills(search);
        Long count = billMapper.selectBillCount(search);

        log.info("billLists : {}", billList);

        return new ListResponse<>(count, billList);
    }

}
