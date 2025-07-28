package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository.RevokeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.repository.RevokeAgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.message.service.MsgService;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RevokeServiceImpl implements RevokeService {

	private final RevokeMapper revokeMapper;
	private final ApplyMapper applyMapper;
	private final ProcessService processService;
	private final ComFileService comFileService;
	private final BillMngService billMngService;
	private final RevokeAgreeMapper revokeAgreeMapper;
	private final MsgService msgService;

	/**
	 * Retrieves a list of revocation requests based on the provided parameters.
	 *
	 * @param param a HashMap containing parameters to filter the list of revocation requests.
	 *              It includes details such as the user ID (modified here to include "ppsrId").
	 * @return a list of ApplyVo objects representing the filtered revocation requests.
	 */
	@Override
	public List<ApplyVo> getRevokeList(HashMap<String, Object> param) {
		String userId = new SecurityInfoUtil().getAccountId();
//		param.put("userId", userId);
//		return revokeMapper.selectRevokeList(param);
		param.put("ppsrId", userId);
		return applyMapper.selectListBillRevoke(param);
	}

	/**
	 * Retrieves the details of a revoke operation for a specified billId and language.
	 *
	 * @param billId the unique identifier of the bill for which revoke details are to be fetched
	 * @param lang the language preference for fetching the revoke details
	 * @return a {@link RevokeResponse} object containing the revoke details, proposer list, and associated file list
	 */
	@Override
	public RevokeResponse getRevokeDetail(String billId, String lang) {
		RevokeResponse result = new RevokeResponse();
		HashMap<String, Object> param = new HashMap<>();

		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		param.put("billId", billId);
		param.put("lang", lang);

		RevokeVo revokeDetail = revokeMapper.selectRevokeDetail(param);
		result.setRevokeDetail(revokeDetail);

		List<RevokeVo> proposerList = revokeMapper.selectProposerList(param);
		result.setProposerList(proposerList);

		//File list
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(param);
		result.setFileList(fileList);

		return result;
	}

	/**
	 * Processes the revocation of a bill request, updates the required bill details,
	 * manages related files, and coordinates the necessary workflow steps in the revocation process.
	 *
	 * @param billId the unique identifier of the bill to be revoked
	 * @param vo an instance of RevokeVo containing revocation details such as files and remarks
	 * @return an instance of ProcessVo containing process information after handling the revocation
	 */
	@Transactional
	@Override
	public ProcessVo billRevokeRequest(String billId,RevokeVo vo) {

		BillMngVo billMngVo = new BillMngVo();
		billMngVo.setBillId(billId);
		billMngVo.setRmrkKg(vo.getWtCnKg());
		billMngVo.setRmrkRu(vo.getWtCnRu());
		billMngVo.setFiles(vo.getFiles());
		billMngVo.setMyFileIds(vo.getMyFileIds());
		billMngVo.setClsCd("400"); //Withdrawal
		billMngVo.setFileKindCd("170"); //Agenda
		try {
			billMngService.insertBillDetail(billMngVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		this.updateRevoke(billId, vo);
//		if(vo.getFiles() != null) {
//			String[] fileKindCd = new String[vo.getFiles().length];
//			for(int i = 0; i < vo.getFiles().length; ++i) {
//				fileKindCd[i] = "170"; //Withdrawal
//			}
//			comFileService.saveFileEbs(vo.getFiles(), fileKindCd, billId);
//		}

		// Voter Withdrawal
		String userId = new SecurityInfoUtil().getAccountId();
		HashMap<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("billId", billId);
		param.put("agreeYn", "Y");
		revokeAgreeMapper.updateRevokeAgree(param);

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("1100"); //Agenda withdrawal management
//		pVo.setTaskId(vo.getTaskId());
		processService.handleProcess(pVo);



		return pVo;
	}

	/**
	 * Submits a request to revoke a bill process based on the provided bill ID and revoke information.
	 *
	 * @param billId The unique identifier of the bill to be revoked.
	 * @param vo     The object containing details necessary for the revoke operation.
	 * @return A ProcessVo object containing details about the process after the revoke submission.
	 */
	@Transactional
	@Override
	public ProcessVo billRevokeSubmit(String billId,RevokeVo vo) {

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("1150"); //Agenda withdrawal management
		processService.handleProcess(pVo);

		return pVo;
	}

	/**
	 * Cancels the revocation of a bill and performs necessary operations
	 * such as undoing the process, deleting bill details,
	 * and sending notifications to relevant parties.
	 *
	 * @param billId the unique identifier of the bill to revoke cancel
	 * @param param a map containing additional parameters required for the operation
	 * @return an integer indicating the status or result of the operation
	 */
	@Transactional
	@Override
	public int billRevokeCancel(String billId, HashMap<String, Object> param) {
		processService.undoProcess(billId, "1100");

		BillMngVo billMngVo = new BillMngVo();
		billMngVo.setBillId(billId);
		billMngVo.setFileKindCd("170"); //Agenda
		billMngVo.setClsCd("400"); //Withdrawal
		billMngService.deleteBillDetail(billMngVo);

//		EbsFileVo ebsFileVo = new EbsFileVo();
//		ebsFileVo.setBillId(billId);
//		ebsFileVo.setFileKindCd("170"); //Withdrawal
//		ebsFileVo.setModId(new SecurityInfoUtil().getAccountId());
//		billMngService.updateEbsFileDelYn(ebsFileVo);

		//Withdrawal To the lawmaker alarm
		String msgSj = "Withdrawal";
		String msgCn = "Canceled withdrawal";
		List<String> rcvIds = new ArrayList<>();

		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		param.put("billId", billId);
		List<RevokeVo> proposerList = revokeMapper.selectProposerList(param);
		for(RevokeVo proposer : proposerList) {
			if(!userId.equals(proposer.getProposerId()) && "Y".equals(proposer.getRevokeYn())) {
				rcvIds.add(proposer.getProposerId());
			}
		}

		MsgRequest msgRequest = new MsgRequest();
		msgRequest.setMsgSj(msgSj);
		msgRequest.setMsgCn(msgCn);
		msgRequest.setRcvIds(rcvIds);
		msgService.sendMsg(msgRequest);

		return 0;
	}

//	@Override
//	public boolean hasEveryProposerAgreedToRevoke(String billId) {
//		HashMap<String, Object> param = new HashMap<>();
//		param.put("billId", billId);
//		List<RevokeVo> proposerList = revokeMapper.selectProposerList(param);
//		if(proposerList == null) {
//			return false;
//		}
//
//		for(RevokeVo proposer : proposerList) {
//			if(!"Y".equals(proposer.getRevokeYn())) {
//				return false;
//			}
//		}
//		return true;
//	}

	/**
	 * Updates the revoke status of a bill identified by the given bill ID.
	 *
	 * @param billId The unique identifier of the bill to be updated.
	 * @param revokeVo The revoke value object containing the updated information.
	 * @return The number of rows affected by the update operation.
	 */
	@Override
	public int updateRevoke(String billId, RevokeVo revokeVo) {
		revokeVo.setBillId(billId);
		revokeVo.setModId(new SecurityInfoUtil().getAccountId());
		return revokeMapper.updateRevoke(revokeVo);
	}

}