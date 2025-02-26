package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
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
    	billMngVo.setEbsfileList(fileList);

    	List<BillMngVo> cmtList = billMngMapper.selectEbsMasterCmtList(argVo);
    	List<MtngAllVo> cmtMeetingList = billMngMapper.selectListCmtMeetingList(argVo);//committee meeting list

    	BillMngVo billlegalReviewVo = new BillMngVo();//bill legal review department
    	List<BillMngVo> billLangReviewVoList = new ArrayList<BillMngVo>();//bill Language review department
    	List<BillMngVo> billCmtReviewList = new ArrayList<BillMngVo>();//bill Committee review department

    	for(BillMngVo listVo : billEtcInfoList) {

    		String clsCd = listVo.getClsCd();
    		//if(argVo.getDeptCd().equals(listVo.getDeptCd())) {

        		if("110".equals(clsCd)) {//법률검토결과
        			billlegalReviewVo = listVo;
        		} else if("120".equals(clsCd)) {//위원언어전문파트
        			billLangReviewVoList.add(listVo);
        		} else if("140".equals(clsCd)) {// Bill detail info Committee Review
        			billCmtReviewList.add(listVo);
        		}
    		//}
    	}

    	for(BillMngVo vo : billCmtReviewList) {
    		Long seq = vo.getSeq();
    		List<EbsFileVo> cmtFileList = new ArrayList<EbsFileVo>();

    		if(argVo.getDeptCd().equals(vo.getDeptCd())) {
        		for(EbsFileVo fvo:fileList) {
        			if(seq.equals(fvo.getDetailSeq())) {
        				cmtFileList.add(fvo);
        			}
        		}
        		vo.setEbsfileList(cmtFileList);
    		}
    	}

    	//List<ProposerVo> proposerList = billMngMapper.selectProposerMemberList(param);
    	//List<BillMngVo> cmtList = billMngMapper.selectCmtList(param);

    	BillMngResponse billMngResponse = new BillMngResponse();
    	billMngResponse.setBillMngVo(billMngVo);
    	billMngResponse.setBillEtcInfoList(billEtcInfoList);
    	billMngResponse.setBilllegalReviewVo(billlegalReviewVo);
    	billMngResponse.setBillLangReviewVoList(billLangReviewVoList);
    	billMngResponse.setBillCmtReviewVoList(billCmtReviewList);
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
		billMngVo.setModId(loginId);

		BillMngVo billDetailVo = billMngMapper.selectOnelegalReview(billMngVo);
		if(billDetailVo == null) {
			billMngMapper.insertBillDetail(billMngVo);
		} else {
			billMngMapper.updateBillDetail(billMngVo);
		}

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


	@Override
	public BillMngVo insertCmtMeetingRvReport(BillMngVo billMngVo) {

		BillMngVo resultVo = new BillMngVo();
		String loginId = new SecurityInfoUtil().getAccountId();
		billMngVo.setRegId(loginId);
		/*bill detail info insert*/
		billMngMapper.insertCmtMeetingRvReport(billMngVo);

		/*bill detail file info insert*/
		comFileService.saveFileBillDetailMng(billMngVo);

		resultVo.setSeq(billMngVo.getSeq());
		resultVo.setBillId(billMngVo.getBillId());

		return resultVo;
	}

	@Override
	public BillMngVo deleteCmtReview(BillMngVo billMngVo) {

		String loginId = new SecurityInfoUtil().getAccountId();
		billMngVo.setModId(loginId);
		billMngMapper.deleteCmtReview(billMngVo);
		billMngMapper.deleteCmtReviewEbsFile(billMngVo);

		return billMngVo;
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