package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository.BillAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository.BillMngMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillMngServiceImpl implements BillMngService {

	private final BillAllMapper billAllMapper;
	private final BillMngMapper billMngMapper;
    private final ProcessService processService;
    private final ComFileService comFileService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BillMngServiceImpl.class);
	private final ProcessMapper processMapper;
	private final ApplyMapper applyMapper;

	/**
	 * Retrieves a list of BillMngVo objects based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters required for fetching the bill list
	 * @return a List of BillMngVo objects that match the criteria specified in the parameters
	 */
    @Override
    public List<BillMngVo> getBillList(HashMap<String, Object> param) {

        List<BillMngVo> result = billMngMapper.selectListBillMng(param);
        return result;
    }

	/**
	 * Retrieves a single bill's details including its associated file list based on the provided parameters.
	 *
	 * @param param The BillMngVo object containing the search criteria for retrieving the bill.
	 * @return A BillMngResponse object containing the retrieved bill details and its associated files.
	 */
	@Override
	public BillMngResponse selectOneBill(BillMngVo param) {

		BillMngVo billMngVo = billMngMapper.selectOneBillByGd(param);//bill basic info
		List<EbsFileVo> fileList = billMngMapper.selectFileList(param);
		billMngVo.setEbsfileList(fileList);
    	BillMngResponse billMngResponse = new BillMngResponse();
    	billMngResponse.setBillMngVo(billMngVo);

		return billMngResponse;
	}





    @Override
    public BillMngResponse getBillById(HashMap<String, Object> param) {

    	String deptCd = new SecurityInfoUtil().getDeptCd();

    	BillMngVo billMngVo = billMngMapper.selectBill(param);//bill basic info

    	ProcessVo processVo = null;
    	if(param.get("taskId") != null) {
    		Long taskId = Long.valueOf((String)param.get("taskId"));
    		ProcessVo vo = new ProcessVo();
    		vo.setTaskId(taskId);
    		processVo = processMapper.selectBpTask(vo);
    	}

    	List<BillMngVo> billEtcInfoList = billMngMapper.selectListBillEtcInfo(param);
//    	List<EbsFileVo> fileList = billMngMapper.selectFileList(argVo);
//    	billMngVo.setEbsfileList(fileList);

		//file List
		List<EbsFileVo> fileList = applyMapper.selectBillFileList(param);
		billMngVo.setEbsfileList(fileList);

		//List of proposed documents.
		List<EbsFileVo> applyFileList = applyMapper.selectApplyFileList(param);
		billMngVo.setApplyFileList(applyFileList);


    	List<BillMngVo> cmtList = billMngMapper.selectEbsMasterCmtList(param);
    	List<MtngAllVo> cmtMeetingList = billMngMapper.selectListCmtMeetingList(param);//committee meeting list

    	BillMngVo billlegalReviewVo = new BillMngVo();//bill legal review department
    	List<BillMngVo> billLangReviewVoList = new ArrayList<>();//bill Language review department
    	List<BillMngVo> billCmtReviewList = new ArrayList<>();//bill Committee review department

    	for(BillMngVo listVo : billEtcInfoList) {

    		List<EbsFileVo> detailFileList = new ArrayList<>();
    		for(EbsFileVo file : fileList) {
    			if(listVo.getSeq().equals(file.getDetailSeq())) {
    				detailFileList.add(file);
    			}
    		}
    		listVo.setEbsfileList(detailFileList);

    		String clsCd = listVo.getClsCd();

    		if("110".equals(clsCd)) {//Legal review results
    			billlegalReviewVo = listVo;
    		} else if("120".equals(clsCd)) {//Committee language part
    			billLangReviewVoList.add(listVo);
    		} else if( ("160".equals(clsCd) || "190".equals(clsCd)) && deptCd.equals(listVo.getDeptCd()) ) {// Bill detail info Committee Review
    			billCmtReviewList.add(listVo);
    		}
    	}

    	BillMngResponse billMngResponse = new BillMngResponse();
    	billMngResponse.setBillMngVo(billMngVo);
    	billMngResponse.setBillEtcInfoList(billEtcInfoList);
    	billMngResponse.setBilllegalReviewVo(billlegalReviewVo);
    	billMngResponse.setBillLangReviewVoList(billLangReviewVoList);
    	billMngResponse.setBillCmtReviewVoList(billCmtReviewList);
    	billMngResponse.setCmtList(cmtList);
    	billMngResponse.setCmtMeetingList(cmtMeetingList);
    	billMngResponse.setProcessVo(processVo);

        return billMngResponse;
    }


	@Override
	public BillMngResponse selectListBillEtcInfo(HashMap<String, Object> param) {

		BillMngResponse billMngResponse = new BillMngResponse();
		BillMngVo billlegalReviewVo = null;//bill legal review department
		List<BillMngVo> billEtcInfoList = billMngMapper.selectListBillEtcInfo(param);

		ProcessVo processVo = null;
    	if(param.get("taskId") != null) {
    		Long taskId = Long.valueOf((String)param.get("taskId"));
    		ProcessVo vo = new ProcessVo();
    		vo.setTaskId(taskId);
    		processVo = processMapper.selectBpTask(vo);
    	}

    	for(BillMngVo listVo : billEtcInfoList) {

    		String clsCd = listVo.getClsCd();

    		if("110".equals(clsCd)) {//Legal review results
    			billlegalReviewVo = listVo;
    		}

    		HashMap<String, Object> param2 = new HashMap<>();
        	param2.put("detailSeq", listVo.getSeq());
    		List<EbsFileVo> etcFileList = billAllMapper.selectListBillEtcFile2(param2);
    		listVo.setEbsfileList(etcFileList);
    	}

		billMngResponse.setProcessVo(processVo);
		billMngResponse.setBilllegalReviewVo(billlegalReviewVo);
		billMngResponse.setBillEtcInfoList(billEtcInfoList);

		return billMngResponse;
	}


    @Transactional
	@Override
	public BillMngVo billRegisterMng(BillMngVo billMngVo) {

    	String loginId = new SecurityInfoUtil().getAccountId();
    	billMngVo.setModId(loginId);
    	billMngMapper.updateBillno(billMngVo);// New billno create update

    	HashMap<String, Object> param = new HashMap<>();
    	param.put("billId", billMngVo.getBillId());
    	BillAllVo bill = billAllMapper.selectBill(param);
    	billMngVo.setBillNo(bill.getBillNo());

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);

		return billMngVo;
	}

	@Override
	public BillMngVo billCmtRegMng(BillMngVo billMngVo) {

        //committee generation
		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billMngVo.getBillId());
		pVo.setStepId(billMngVo.getStepId());
		pVo.setTaskId(billMngVo.getTaskId());
		processService.handleProcess(pVo);
		return null;
	}


	@Transactional
	@Override
	public BillMngVo insertBillDetail(BillMngVo billMngVo) throws Exception {

		String loginId = new SecurityInfoUtil().getAccountId();
//		String clsCd = "";
		billMngVo.setRegId(loginId);
		billMngVo.setModId(loginId);

		BillMngVo billDetailVo = billMngMapper.selectOnelegalReview(billMngVo);
		if(billDetailVo == null) {
			billMngMapper.insertBillDetail(billMngVo);

//			if("340".equals(clsCd)) {//Submit to the plenary session
//				ProcessVo pVo = new ProcessVo();
//				pVo.setBillId(billMngVo.getBillId());
//				pVo.setStepId("1800");//Plenary audit
//				processService.handleProcess(pVo);
//			}

		} else {
			billMngMapper.updateBillDetail(billMngVo);
		}
		comFileService.saveFileBillDetailMng(billMngVo);

		billMngVo.setFiles(null);
		billMngVo.setFileUploads(null);
		return billMngVo;
	}

	@Transactional
	@Override
	public BillMngVo insertBillPrmg(BillMngVo billMngVo) throws Exception {
		billMngVo.setModId(new SecurityInfoUtil().getAccountId());
		billMngMapper.updateBillMaster(billMngVo);
		return this.insertBillDetail(billMngVo);
	}

	@Transactional
	@Override
	public BillMngVo presidentReject(BillMngVo billMngVo) throws Exception {
//		ProcessVo pVo = new ProcessVo();
//		pVo.setBillId(billMngVo.getBillId());
//		pVo.setStepId("3400"); //Presidential veto
//		pVo.setTaskId(billMngVo.getTaskId());
//		processService.handleProcess(pVo);

		return this.insertBillDetail(billMngVo);
	}

	@Transactional
	@Override
	public void deleteBillDetail(BillMngVo billMngVo) {

		EbsFileVo ebsFileVo = new EbsFileVo();
		ebsFileVo.setBillId(billMngVo.getBillId());
		ebsFileVo.setFileKindCd(billMngVo.getFileKindCd()); //Agenda
		ebsFileVo.setModId(new SecurityInfoUtil().getAccountId());
		billMngMapper.updateEbsFileDelYn(ebsFileVo);

		billMngVo.setClsCd(billMngVo.getClsCd()); //Withdrawal
		billMngMapper.deleteBillDetail(billMngVo);
	}

	@Override
	public BillMngVo insertBillLegalReviewReport(BillMngVo billMngVo) {

		//Current step completion processing
		//Import the next step and insert
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
	public BillMngVo insertCmtMeetingRvReport(BillMngVo billMngVo) throws Exception {

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

//	@Transactional
//	@Override
//	public EbsFileVo insertBillMngFile(EbsFileVo ebsFileVo) {
//		//File registration
//		comFileService.saveFileBillMng(ebsFileVo);
//		//file Information have So nulltreatment
//		ebsFileVo.setFiles(null);
//
//		return ebsFileVo;
//	}

	@Transactional
	@Override
	public BillMngVo insertBillDetailFile(BillMngVo billMngVo) throws Exception {
		//File registration
		comFileService.saveFileBillDetailMng(billMngVo);
		//file Information have So nulltreatment
		billMngVo.setFiles(null);

		return billMngVo;
	}

	@Override
	public EbsFileVo updateEbsFileDelYn(EbsFileVo ebsFileVo) {
		billMngMapper.updateEbsFileDelYn(ebsFileVo);

		return ebsFileVo;
	}

	@Override
	public List<BillMngVo> selectListMainMtSubmit(HashMap<String, Object> param) {
        List<BillMngVo> result = billMngMapper.selectListMainMtSubmit(param);
        return result;
	}



}