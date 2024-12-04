package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository.BillAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillAllServiceImpl implements BillAllService {
    private final BillAllMapper billAllMapper;

    @Override
    public List<BillAllVo> getBillList(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.getBillList(param);
        return result;
    }

    @Override
    public BillAllVo getBillById(String billId) {
    	BillAllVo dto = billAllMapper.getBillById(billId);
        return dto;
    }
}