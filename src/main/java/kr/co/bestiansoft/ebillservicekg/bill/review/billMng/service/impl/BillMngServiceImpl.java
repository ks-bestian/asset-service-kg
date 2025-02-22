package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository.BillMngMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
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
    private final ComFileService comFileService;

    @Override
    public List<BillMngVo> getBillList(HashMap<String, Object> param) {

        List<BillMngVo> result = billMngMapper.selectListBillMng(param);
        return result;
    }

    @Override
    public BillMngResponse getBillById(BillMngVo argVo) {

    	BillMngVo billMngVo = billMngMapper.selectOneBill(argVo);//bill basic info
    	List<BillMngVo> billEtcInfoList = billMngMapper.selectListBillEtcInfo(argVo);
    	List<EbsFileVo> fileList = billMngMapper.selectFileList(argVo);
    	List<BillMngVo> cmtList = billMngMapper.selectEbsMasterCmtList(argVo);
    	List<MtngAllVo> cmtMeetingList = billMngMapper.selectListCmtMeetingList(argVo);//committee meeting list

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

    	//List<ProposerVo> proposerList = billMngMapper.selectProposerMemberList(param);
    	//List<BillMngVo> cmtList = billMngMapper.selectCmtList(param);

    	BillMngResponse billMngResponse = new BillMngResponse();
    	billMngResponse.setBillMngVo(billMngVo);
    	billMngResponse.setBillEtcInfoList(billEtcInfoList);
    	billMngResponse.setBilllegalReviewVo(billlegalReviewVo);
    	billMngResponse.setBillLangReview1stVo(billLangReview1stVo);
    	billMngResponse.setFileList(fileList);
    	billMngResponse.setCmtList(cmtList);
    	billMngResponse.setCmtMeetingList(cmtMeetingList);

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

    	String loginId = new SecurityInfoUtil().getAccountId();
    	billMngVo.setModId(loginId);
    	billMngMapper.updateBillno(billMngVo);// New billno create update

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);

		return billMngVo;
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

		String loginId = new SecurityInfoUtil().getAccountId();
		billMngVo.setRegId(loginId);
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



	@Override
	public List<ProposerVo> selectProposerByBillId(HashMap<String, Object> param) {
		List<ProposerVo> result = billMngMapper.selectProposerByBillId(param);
		return result;
	}

	@Transactional
	@Override
	public BillMngVo insertBillCommitt(BillMngVo billMngVo) {

		billMngMapper.deleteBillCmtByBillId(billMngVo);

		String regId = new SecurityInfoUtil().getAccountId();
		billMngVo.setRegId(regId);
		billMngVo.setCmtSeCd("M");
		billMngMapper.insertBillCmt(billMngVo);

		List<String> relCmts = billMngVo.getRelCmtList();

		if (relCmts != null && !relCmts.isEmpty()) {
			for(String cmtCd : relCmts) {
				billMngVo.setCmtCd(cmtCd);
				billMngVo.setCmtSeCd("R");

				billMngMapper.insertBillCmt(billMngVo);
			}
		}

		return billMngVo;
	}

	@Transactional
	@Override
	public EbsFileVo insertBillMngFile(EbsFileVo ebsFileVo) {
		//파일등록
		comFileService.saveFileBillMng(ebsFileVo);
		//파일 정보를 가지고 있어서 null처리
		ebsFileVo.setFiles(null);

		return ebsFileVo;
	}

	@Override
	public EbsFileVo updateEbsFileDelYn(EbsFileVo ebsFileVo) {
		billMngMapper.updateEbsFileDelYn(ebsFileVo);

		return ebsFileVo;
	}


}