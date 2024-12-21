package kr.co.bestiansoft.ebillservicekg.common.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository.BillMngMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillProcMngVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.UnauthorizedException;
import kr.co.bestiansoft.ebillservicekg.common.service.BillProcessMngService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillProcessMngServiceImpl implements BillProcessMngService {

	private final BillMngMapper billMngMapper;
	private List<BillProcMngVo> procVoList;

	@Override
	public BillProcMngVo makeProc(String procKingCd, String billId) {

		procVoList = billMngMapper.getBillProcMngList(procKingCd);
		BillProcMngVo procVo = null;

		for(BillProcMngVo vo:procVoList) {
			if(vo.getProcKindCd().equals(procKingCd)) {
				procVo = vo;
				break;
			}
		}
		procVo.setBillId(billId);

		accessCheck(procVo);
		processBill(procVo);
		log.info("BillProcMngVo >>"+procVo.toString());

		return procVo;
	}

	private void accessCheck(BillProcMngVo procVo) {

		String sessionAuthId = "21";
		String rcpAuthId = procVo.getRcpAuthId();
		String rcpAuthIds[] = rcpAuthId.split(",");
		boolean exist = Arrays.asList(rcpAuthIds).contains(sessionAuthId);
        if(!exist) {
        	throw new UnauthorizedException("Bill Process request denied. request authId :"+ sessionAuthId+ " , reception authId:"+rcpAuthId);
        }
	}


	private int processBill(BillProcMngVo procVo) {

		String kindCd = procVo.getProcKindCd();

		switch (kindCd) {
	        case "Apple":
	            System.out.println("Apple is red.");
	            break;
	        case "Banana":
	            System.out.println("Banana is yellow.");
	            break;
	        case "Grape":
	            System.out.println("Grape is purple.");
	            break;
	        default:
	            System.out.println("Unknown fruit.");
        }


		return 0;
	}


}
