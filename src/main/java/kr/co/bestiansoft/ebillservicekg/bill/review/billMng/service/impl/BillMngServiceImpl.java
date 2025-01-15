package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository.BillMngMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillMngServiceImpl implements BillMngService {
    private final BillMngMapper billMngMapper;

    @Override
    public List<BillMngVo> getBillList(HashMap<String, Object> param) {

        List<BillMngVo> result = billMngMapper.getBillList(param);
        return result;
    }

    @Override
    public BillMngResponse getBillById(HashMap<String, Object> param) {

    	BillMngVo dto = billMngMapper.getBillById(param);
    	List<ProposerVo> proposerList = billMngMapper.selectProposerMemberList(param);
    	List<BillMngVo> cmtList = billMngMapper.selectCmtList(param);

    	BillMngResponse billMngResponse = new BillMngResponse(dto, proposerList, cmtList);

        return billMngResponse;
    }

    @Transactional
	@Override
	public BillMngVo createBill(BillMngVo billMngVo) {
    	billMngMapper.insertBill(billMngVo);
        for (ProposerVo proposerVo : billMngVo.getProposerList()) {
        	billMngMapper.insertProposers(proposerVo);
        }


    	return billMngVo;
	}
}