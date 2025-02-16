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
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillMngServiceImpl implements BillMngService {

    private final BillMngMapper billMngMapper;
    private final ProcessService processService;

    @Override
    public List<BillMngVo> getBillList(HashMap<String, Object> param) {

        List<BillMngVo> result = billMngMapper.selectListBillMng(param);
        return result;
    }

    @Override
    public BillMngResponse getBillById(BillMngVo argVo) {

    	BillMngVo billMngVo = billMngMapper.selectOneBill(argVo);//bill basic info
    	List<BillMngVo> billEtcInfoList = billMngMapper.selectListBillEtcInfo(argVo);

    	BillMngVo billlegalReviewVo = null;//bill legal review department
    	BillMngVo billLangReview1stVo = null;//bill legal review department

    	for(BillMngVo listVo : billEtcInfoList) {

    		String clsCd = listVo.getClsCd();
    		if("110".equals(clsCd)) {//법률검토결과
    			billlegalReviewVo = listVo;
    		} else if("120".equals(clsCd)) {//위원회1차언어전문파트
    			billLangReview1stVo = listVo;
    		}


    	}

		//BillMngVo billlegalReviewVo = billMngMapper.selectOnelegalReview(argVo);
    	//List<ProposerVo> proposerList = billMngMapper.selectProposerMemberList(param);
    	//List<BillMngVo> cmtList = billMngMapper.selectCmtList(param);

    	BillMngResponse billMngResponse = new BillMngResponse();
    	billMngResponse.setBillMngVo(billMngVo);
    	billMngResponse.setBillEtcInfoList(billEtcInfoList);
    	billMngResponse.setBilllegalReviewVo(billlegalReviewVo);
    	billMngResponse.setBillLangReview1stVo(billLangReview1stVo);

        return billMngResponse;
    }


	@Override
	public List<BillMngVo> selectListBillEtcInfo(BillMngVo argVo) {
		List<BillMngVo> billEtcInfoList = billMngMapper.selectListBillEtcInfo(argVo);
		return billEtcInfoList;
	}




    @Transactional
	@Override
	public BillMngVo billRegisterMng(BillMngVo billMngVo) {

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);

		return null;
	}

	@Override
	public BillMngVo billCmtRegMng(BillMngVo billMngVo) {

        //위원회 생성

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);
		return null;
	}

//	@Override
//	public List<BillMngVo> selectListlegalReview(HashMap<String, Object> param) {
//        List<BillMngVo> result = billMngMapper.selectListlegalReview(param);
//        return result;
//	}


//	@Override
//	public BillMngResponse selectOnelegalReview(HashMap<String, Object> param) {
//
//		BillMngResponse billMngResponse = new BillMngResponse();
//		BillMngVo billMngVo = billMngMapper.selectOnelegalReview(param);
//		billMngResponse.setBillMngVo(billMngVo);
//
//		//////////////////////////
//		ProcessVo pvo = new ProcessVo();
//		pvo.setTaskId(Long.valueOf(String.valueOf(param.get("taskId"))));
//		ProcessVo taskVo = processService.selectBpTask(pvo);
//		billMngResponse.setProcessVo(taskVo);
//
//		return billMngResponse;
//	}

	@Transactional
	@Override
	public BillMngVo insertBillDetail(BillMngVo billMngVo) {
		billMngMapper.insertBillDetail(billMngVo);
		return billMngVo;
	}

	@Override
	public BillMngVo insertBillLegalReviewReport(BillMngVo billMngVo) {

		//현재 스텝 완료처리
		//다음스텝가져와서 인서트
		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);

		return null;
	}


	@Override
	public List<BillMngVo> selectListCmtReviewReport(HashMap<String, Object> param) {
        List<BillMngVo> result = billMngMapper.selectListCmtReviewReport(param);
        return result;
	}





	///////////////////////////////////////////////////////////////////



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