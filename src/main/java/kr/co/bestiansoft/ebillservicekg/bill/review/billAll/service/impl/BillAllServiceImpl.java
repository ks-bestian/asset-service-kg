package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.repository.BillAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo.BillAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class BillAllServiceImpl implements BillAllService {

    private final BillAllMapper billAllMapper;
    private final AgreeMapper agreeMapper;
	private final ApplyMapper applyMapper;

	/**
	 * Retrieves a list of bills based on the provided parameters.
	 *
	 * @param param a map containing the parameters for the query
	 * @return a list*/
    @Override
    public List<BillAllVo> getBillList(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.selectListBill(param);
        return result;
    }

	/**
	 * Retrieves the bill information based on the provided bill ID and additional parameters.
	 *
	 * @param billId The unique identifier of the bill to fetch details for.
	 * @param param A map containing any additional parameters needed for the query.
	 * @return A {@code BillAllResponse} object containing the complete details of the bill,
	 *         including basic information, proposer list, file lists, committee information,
	 *         meeting details, and other related data.
	 */
    @Override
    public BillAllResponse getBillById(String billId, HashMap<String, Object> param) {

    	BillAllResponse billRespanse = new BillAllResponse();
    	param.put("billId", billId);

    	/* Bill basic info */
    	BillAllVo billBasicInfo = billAllMapper.selectBill(param);
    	/* Proposer List */
    	List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);

    	/* Proposer String */
    	if(proposerList != null) {
    		String proposerItem = proposerList.stream().map(AgreeVo::getMemberNm).collect(Collectors.joining(", "));
        	billBasicInfo.setProposerItems(proposerItem);
    	}

//    	/* Bill doc list*/
//    	List<EbsFileVo> billFileList = billAllMapper.selectListBillFile(param);
//
//    	/* Bill Etc doc list*/
//    	List<EbsFileVo> etcFileList = billAllMapper.selectListBillEtcFile(param);

    	//file List
		List<EbsFileVo> fileList = applyMapper.selectBillFileList(param);

		//List of proposed documents
		List<EbsFileVo> applyFileList = applyMapper.selectApplyFileList(param);

    	/*committee list*/
    	List<BillAllVo> cmtList = billAllMapper.selectListBillCmt(param);

    	for(BillAllVo cmtVo: cmtList) {

    		List<EbsFileVo> cmtReviewFileList = new ArrayList<>();
    		for(EbsFileVo fileVo:fileList) {

    			if(    cmtVo.getCmtCd().equals(fileVo.getDeptCd())
    				&& ("160".equals(fileVo.getClsCd()) || "190".equals(fileVo.getClsCd())) ) {//Bill Committee Review
    				cmtReviewFileList.add(fileVo);
    			}
    		}
    		cmtVo.setFileList(cmtReviewFileList);
    	}

    	//  selectListMettingResultFile

    	/* committee meeting list*/
    	List<MtngAllVo> cmtMtList = billAllMapper.selectListCmtMeeting(param);
    	for(MtngAllVo vo : cmtMtList) {
    		List<MtngFileVo> mtFileList = billAllMapper.selectListMettingResultFile(vo.getMtngId());
    		vo.setReportList(mtFileList);
    	}

    	/* main meeting list*/
    	List<MtngAllVo> mainMtList = billAllMapper.selectListMainMeeting(param);
    	for(MtngAllVo vo : mainMtList) {
    		List<MtngFileVo> mtFileList = billAllMapper.selectListMettingResultFile(vo.getMtngId());
    		vo.setReportList(mtFileList);
    	}

    	/* Party meeting list*/
    	List<MtngAllVo> partyMtList = billAllMapper.selectListPartyMeeting(param);
    	for(MtngAllVo vo : partyMtList) {
    		List<MtngFileVo> mtFileList = billAllMapper.selectListMettingResultFile(vo.getMtngId());
    		vo.setReportList(mtFileList);
    	}

    	/* Bill etc info */
    	List<BillAllVo> etcInfoList = billAllMapper.selectListBillEtcInfo(param);

    	BillAllVo billlegalReviewVo = new BillAllVo();//bill legal review department
    	List<BillAllVo> billLangReviewVoList = new ArrayList<>();//bill Language review department

    	for(BillAllVo listVo : etcInfoList) {

    		List<EbsFileVo> detailFileList = new ArrayList<>();
    		for(EbsFileVo file : fileList) {
    			if(listVo.getSeq().equals(file.getDetailSeq())) {
    				detailFileList.add(file);
    			}
    		}
    		listVo.setFileList(detailFileList);

    		String clsCd = listVo.getClsCd();
    		if("110".equals(clsCd)) {//Legal review results
    			billlegalReviewVo = listVo;
    		} else if("120".equals(clsCd)) {//Committee language part
    			billLangReviewVoList.add(listVo);
    		}
    	}

    	billRespanse.setBillBasicInfo(billBasicInfo);
    	billRespanse.setProposerList(proposerList);
    	billRespanse.setBillFileList(applyFileList);
    	billRespanse.setCmtList(cmtList);
    	billRespanse.setCmtMtList(cmtMtList);
    	billRespanse.setMainMtList(mainMtList);
    	billRespanse.setPartyMtList(partyMtList);
    	billRespanse.setBillLangReviewVoList(billLangReviewVoList);
    	billRespanse.setBilllegalReviewVo(billlegalReviewVo);
    	billRespanse.setEtcInfoList(etcInfoList);

        return billRespanse;
    }

	/**
	 * Retrieves a list of monitored bills based on the provided parameters.
	 *
	 * @param param a map containing the parameters for the query
	 * @return a list of {@code BillAllVo} objects representing the monitored bills
	 */
	@Override
	public List<BillAllVo> selectListBillMonitor(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.selectListBillMonitor(param);
        return result;
	}

	/**
	 * Retrieves a list of bills categorized by a specific type based on the provided parameters.
	 *
	 * @param param a map containing the parameters for categorizing and querying the bills
	 * @return a list of {@code BillAllVo} objects representing the categorized bills
	 */
	@Override
	public List<BillAllVo> countBillByPpslKnd(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.countBillByPpslKnd(param);
        return result;
	}

	/**
	 * Retrieves a list of bills categorized by political affiliation based on the provided parameters.
	 *
	 * @param param a map containing the parameters for categorizing and querying the bills
	 * @return a list of {@code BillAllVo} objects representing the categorized bills
	 */
	@Override
	public List<BillAllVo> countBillByPoly(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.countBillByPoly(param);
        return result;
	}

	/**
	 * Counts and retrieves a list of bills based on the given parameters.
	 * The parameters are typically used to filter the bill data by community or other criteria.
	 *
	 * @param param a HashMap containing the filter criteria for the bill count operation.
	 *              The keys and values in the map should match the expected query parameters.
	 * @return a List of BillAllVo objects representing the bills matching the provided criteria.
	 */
	@Override
	public List<BillAllVo> countBillByCmt(HashMap<String, Object> param) {

        List<BillAllVo> result = billAllMapper.countBillByCmt(param);
        return result;
	}

}