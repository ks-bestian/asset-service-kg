package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ArrayNode;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import kr.co.bestiansoft.ebillservicekg.test.domain.CommentsHierarchy;
import kr.co.bestiansoft.ebillservicekg.test.repository2.HomePageMapper;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ApplyServiceImpl implements ApplyService {

	private final ApplyMapper applyMapper;
	private final AgreeMapper agreeMapper;
	private final ProcessMapper processMapper;
	private final ComFileService comFileService;
	private final ProcessService processService;
	private final HomePageMapper homePageMapper;
	private final BillMngService billMngService;

	/**
	 * Creates an application process, assigns a unique Bill ID, associates the request data,
	 * handles file management, manages the proposer list, and starts the process workflow.
	 *
	 * @param applyVo the ApplyVo object containing the application data to be processed
	 * @return the processed ApplyVo object with updated and persisted information
	 * @throws Exception if there is any error during the application creation process
	 */
	@Transactional
	@Override
	public ApplyVo createApply(ApplyVo applyVo) throws Exception {
	//TODO :: 메세지 알람 적용해야함

		//안건등록
		String billId = StringUtil.getEbillId();
		applyVo.setBillId(billId);
		String ppsrId = new SecurityInfoUtil().getAccountId();

		applyVo.setPpsrId(ppsrId);
		applyVo.setRegId(ppsrId);
		applyMapper.insertApplyBill(applyVo);

//		//파일등록
//		comFileService.saveFileEbs(applyVo.getFiles(), applyVo.getFileKindCds(), applyVo.getOpbYns(), billId);
//
//		//추가 - 내 문서함에서 파일 업로드(20250221 조진호)
//		comFileService.saveFileEbs(applyVo.getMyFileIds(), applyVo.getFileKindCds2(), applyVo.getOpbYns2(), billId);

		BillMngVo billMngVo = new BillMngVo();
		billMngVo.setFileUploads(applyVo.getFileUploads());
		billMngVo.setBillId(applyVo.getBillId());
		comFileService.saveFileBillDetailMng(billMngVo);

		//파일 정보를 가지고 있어서 null처리
		applyVo.setFiles(null);
		applyVo.setFileUploads(null);

		//발의자 요청
		List<String> proposerList = applyVo.getProposerList();
		if(proposerList == null) {
			proposerList = new ArrayList<>();
		}

		if(!proposerList.contains(applyVo.getPpsrId())) {
			proposerList.add(applyVo.getPpsrId());
		}

		int ord = proposerList.size();
		for(String memberId : proposerList) {
			ApplyVo member = applyMapper.getProposerInfo(memberId);

			if(member == null) {
				break;
			}

			applyVo.setOrd(++ord);
			applyVo.setPolyCd(member.getPolyCd());
			applyVo.setPolyNm(member.getPolyNm());
			applyVo.setPpsrId(member.getMemberId());

			if(member.getMemberId().equals(ppsrId)) {
				applyVo.setSignDt("sign");
			}
			applyMapper.insertProposerList(applyVo);
		}


		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("PC_START");//안건생성 프로세스시작
		processService.handleProcess(pVo);

		return applyVo;
	}

	/**
	 * Creates and registers an application with the provided data, involving various processes such
	 * as generating a bill ID, saving file information, and processing proposer lists.
	 *
	 * @param applyVo the data object containing information necessary for creating and registering
	 *                the application, including details about the proposer list, files, and other attributes
	 * @return the updated ApplyVo object representing the registered application, including generated IDs
	 *         and other processed data
	 * @throws Exception if any error occurs during the process of registration or related operations
	 */
	@Transactional
	@Override
	public ApplyVo createApplyRegister(ApplyVo applyVo) throws Exception {
		//안건등록
		String billId = StringUtil.getEbillId();
		applyVo.setBillId(billId);
		applyVo.setRegId(new SecurityInfoUtil().getAccountId());

		applyMapper.insertApplyBill(applyVo);

//		//파일등록
//		comFileService.saveFileEbs(applyVo.getFiles(), applyVo.getFileKindCds(), applyVo.getOpbYns(), billId);
//
//		//추가 - 내 문서함에서 파일 업로드(20250221 조진호)
//		comFileService.saveFileEbs(applyVo.getMyFileIds(), applyVo.getFileKindCds2(), applyVo.getOpbYns2(), billId);

		BillMngVo billMngVo = new BillMngVo();
		billMngVo.setFileUploads(applyVo.getFileUploads());
		billMngVo.setBillId(applyVo.getBillId());
		comFileService.saveFileBillDetailMng(billMngVo);

		//파일 정보를 가지고 있어서 null처리
		applyVo.setFiles(null);
		applyVo.setFileUploads(null);

		//발의자 요청
		List<String> proposerList = applyVo.getProposerList();
		if(proposerList == null) {
			proposerList = new ArrayList<>();
		}

		int ord = proposerList.size();
		String ppsrId = applyVo.getPpsrId();
		for(String memberId : proposerList) {
			ApplyVo member = applyMapper.getProposerInfo(memberId);

			if(member == null) {
				break;
			}

			applyVo.setOrd(++ord);
			applyVo.setPolyCd(member.getPolyCd());
			applyVo.setPolyNm(member.getPolyNm());
			applyVo.setPpsrId(member.getMemberId());

//			if(member.getMemberId().equals(ppsrId)) {
//				applyVo.setSignDt("sign");
//			}
//			else {
//				applyVo.setSignDt(null);
//			}
			applyVo.setSignDt("sign");
			applyMapper.insertProposerList(applyVo);
		}

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("PC_START");//안건생성 프로세스시작
		processService.handleProcess(pVo);

		//접수요청 프로세스
		pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("0");
		ProcessVo task = processMapper.selectBpStepTasks(pVo).get(0);

		applyVo.setTaskId(task.getTaskId());
		applyVo.setStepId("1000");
		this.saveBillAccept(billId, applyVo);

		//안건접수
		pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("1000");
		task = processMapper.selectBpStepTasks(pVo).get(0);

		BillMngVo billMngVo2 = new BillMngVo();
		billMngVo2.setBillId(billId);
		billMngVo2.setRcpDt(applyVo.getRcpDt());
		billMngVo2.setStepId("1200");
		billMngVo2.setTaskId(task.getTaskId());
		billMngService.billRegisterMng(billMngVo2);

		applyVo.setBillNo(billMngVo2.getBillNo());

		return applyVo;
	}

	/**
	 * Retrieves a list of ApplyVo objects based on the provided parameters.
	 * The method depends on the current logged-in user's account ID to filter the results.
	 *
	 * @param param a HashMap containing the query parameters used for fetching the ApplyVo list.
	 *              The map should include all relevant search criteria except the login ID,
	 *              which is automatically added within the method.
	 * @return a list of ApplyVo objects that match the given parameters.
	 */
	@Override
	public List<ApplyVo> getApplyList(HashMap<String, Object> param) {
		String loginId = new SecurityInfoUtil().getAccountId();
//		param.put("loginId", loginId);
//		return applyMapper.selectListApply(param);
		param.put("ppsrId", loginId);
		return applyMapper.selectListBillApply(param);
	}

	/**
	 * Updates the "apply" information associated with a specific bill.
	 * This method is transactional and ensures that all operations succeed together or fail together.
	 * It manages the proposers of the bill, updates file information, and modifies the bill details.
	 *
	 * @param applyVo an ApplyVo object that contains the updated data for the bill, including information about proposers and files
	 * @param billId the unique identifier of the bill to be updated
	 * @return the number of rows affected in the database
	 * @throws Exception if any error occurs during the update process
	 */
	@Transactional
	@Override
	public int updateApply(ApplyVo applyVo, String billId) throws Exception {
		//TODO :: 1. 메세지 알림 기능 적용 필요
		String loginId = new SecurityInfoUtil().getAccountId();

		//발의자 변경
		applyVo.setBillId(billId);

		List<String> newProposerList = applyVo.getProposerList();
		if(newProposerList == null) {
			newProposerList = new ArrayList<>();
		}
	    List<String> oldProposerList = applyMapper.getProposerList(billId);
	    if (!newProposerList.contains(loginId)) {
	    	newProposerList.add(loginId);
	    }

	    //발의자 변경 여부 확인....순서 상관없이 내용만 비교
	    if (!new HashSet<>(newProposerList).equals(new HashSet<>(oldProposerList))) {

			// 발의자 삭제
			List<String> proposerToRemove = new ArrayList<>(oldProposerList);
			proposerToRemove.removeAll(newProposerList);

			for(String ppsrId : proposerToRemove) {
				applyMapper.deleteProposerByPpsrId(ppsrId);
			}

			// 발의자 추가
			List<String> proposerToAdd = new ArrayList<>(newProposerList);
			proposerToAdd.removeAll(oldProposerList);

			int ord = oldProposerList.size();
			for(String ppsrId : proposerToAdd) {
		    	ApplyVo member = applyMapper.getProposerInfo(ppsrId);

		        applyVo.setOrd(++ord);
				applyVo.setPolyCd(member.getPolyCd());
				applyVo.setPolyNm(member.getPolyNm());
				applyVo.setPpsrId(member.getMemberId());
				if(member.getMemberId().equals(loginId)) {
					applyVo.setSignDt("sign");
				}
				applyMapper.insertProposerList(applyVo);
			}
	    }

//		//파일변경
//		if (applyVo.getFiles() != null) {
//			comFileService.saveFileEbs(applyVo.getFiles(), applyVo.getFileKindCds(), applyVo.getOpbYns(), billId);
//			applyVo.setFiles(null);
//		}
//		if(applyVo.getMyFileIds() != null) {
//			comFileService.saveFileEbs(applyVo.getMyFileIds(), applyVo.getFileKindCds2(), applyVo.getOpbYns2(), billId);
//		}
	    BillMngVo billMngVo = new BillMngVo();
		billMngVo.setFileUploads(applyVo.getFileUploads());
		billMngVo.setBillId(billId);
		comFileService.saveFileBillDetailMng(billMngVo);


		//bill update
		applyVo.setLoginId(loginId);
		return applyMapper.updateApplyByBillId(applyVo);
	}

	/**
	 * Deletes the application and associated data based on the provided bill ID.
	 * This method performs the following operations:
	 * - Deletes proposer data associated with the given bill ID.
	 * - Deletes any file data linked to the bill ID and the current user.
	 * - Removes tasks and process instances tied to the bill ID.
	 * - Deletes the application record associated with the bill ID.
	 *
	 * @param billId the unique identifier of the bill to be deleted
	 * @return the number of rows affected in the database
	 */
	@Transactional
	@Override
	public int deleteApply(String billId) {
		applyMapper.deleteProposerByBillId(billId);

		HashMap<String, Object> param = new HashMap<>();
		param.put("userId", new SecurityInfoUtil().getAccountId());
		param.put("billId", billId);
		applyMapper.deleteBillFileByBillId(param);

		ProcessVo processVo = new ProcessVo();
		processVo.setBillId(billId);
		processMapper.deleteBpTasks(processVo);
		processMapper.deleteBpInstance(processVo);


		return applyMapper.deleteApplyByBillId(billId);
	}

	/**
	 * Retrieves detailed information regarding a specific bill application.
	 *
	 * @param billId The unique identifier of the bill to retrieve details for.
	 * @param param  A HashMap containing additional parameters required for the query, such as stepId and trgtUserId.
	 * @return An ApplyResponse object containing the detailed information of the bill, including application details,
	 *         associated files, proposer list, comments hierarchy, and process information.
	 */
	@Override
	public ApplyResponse getApplyDetail(String billId, HashMap<String, Object> param) {

		ApplyResponse result = new ApplyResponse();

		//안건 상세
		param.put("billId", billId);
		param.put("userId", new SecurityInfoUtil().getAccountId());
//		ApplyVo applyDetail = applyMapper.selectApplyDetail(param);
		ApplyVo applyDetail = applyMapper.selectBill(param);
		result.setApplyDetail(applyDetail);

		//파일 리스트
		List<EbsFileVo> fileList = applyMapper.selectBillFileList(param);
		result.setFileList(fileList);

		//발의문서 리스트
		List<EbsFileVo> applyFileList = applyMapper.selectApplyFileList(param);
		result.setApplyFileList(applyFileList);

		//발의자 대상
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);

		//안건 의견 목록
//		if(applyDetail.getSclDscRcpNmb() != null && !"".equals(applyDetail.getSclDscRcpNmb())) {
//			List<CommentsVo> commentList = homePageMapper.selectCommentsByLawId(Long.valueOf(applyDetail.getSclDscRcpNmb()));
//			result.setCommentList(commentList);
//		}

		//안건 댓글
		List<CommentsVo> commentList = homePageMapper.selectCommentsByLawId(null);
//		result.setCommentList(commentList);

		CommentsHierarchy ch = new CommentsHierarchy();
		ch.buildCommentsHierarchy(commentList);
		ArrayNode nodes = ch.getCommentsJson();
		result.setCommentLists(nodes);

		//안건 프로세스정보가져오기.
		ProcessVo pcParam = new ProcessVo();
		pcParam.setBillId(billId);
		pcParam.setStepId(String.valueOf(param.get("stepId")));
		pcParam.setTrgtUserId(String.valueOf(param.get("trgtUserId")));
		ProcessVo pcVo = processMapper.selectBpTaskInfo(pcParam);
		result.setProcessVo(pcVo);

		return result;
	}

	/**
	 * Applies a bill by performing insertion into a bill process and updating its status.
	 *
	 * @param billId the identifier for the bill to be processed and updated
	 * @return the number of rows affected during the update operation
	 */
	@Transactional
	@Override
	public int applyBill(String billId) {

		String statCd = "ST010";
		String procId = StringUtil.getEbillProcId();
		String procKndCd = "PC010";

		applyMapper.insertBillProcess(billId, procId, procKndCd);

		return applyMapper.updateApplyBill(billId, statCd);
	}

	/**
	 * Revokes a bill by updating its status and performing necessary database operations.
	 *
	 * @param billId the unique identifier of the bill to be revoked
	 * @param applyVo the object containing the details required for the revocation process
	 * @return the number of affected rows in the database after updating the bill's status
	 */
	@Transactional
	@Override
	public int revokeBill(String billId, ApplyVo applyVo) {

		String statCd = "ST310";
		applyVo.setStatCd(statCd);
		applyVo.setBillId(billId);
		applyMapper.updateBillPpsrRevoke(applyVo);

		return applyMapper.updateRevokeBill(applyVo);
	}

	/**
	 * Updates the status of a bill with the provided bill ID and application details.
	 *
	 * @param billId the ID of the bill to update
	 * @param applyVo the application details containing the new status and related information
	 * @return the number of records updated in the database
	 */
	@Transactional
	@Override
	public int updateBillStatus(String billId, ApplyVo applyVo) {
		applyVo.setBillId(billId);
		return applyMapper.updateBillStatus(applyVo);
	}

	/**
	 * Saves the bill acceptance process by updating the reception details for the bill
	 * and handling the associated process workflow steps.
	 *
	 * @param billId the unique identifier of the bill to be accepted
	 * @param applyVo an instance of ApplyVo containing information related to the bill acceptance
	 * @return the updated ApplyVo instance after processing
	 */
	@Transactional
	@Override
	public ApplyVo saveBillAccept(String billId, ApplyVo applyVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		applyVo.setBillId(billId);
		applyVo.setModId(userId);
		applyMapper.updateBillRecptnDt(applyVo);

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId(applyVo.getStepId());
		pVo.setTaskId(applyVo.getTaskId());
		processService.handleProcess(pVo);

		return applyVo;
	}

	/**
	 * Deletes a bill file by updating its status in the database.
	 *
	 * @param ebsFileVo the object containing file information to be deleted
	 * @return an integer indicating the number of records updated in the database
	 */
	@Override
	public int deleteBillFile(EbsFileVo ebsFileVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		return applyMapper.updateFileDelete(ebsFileVo, userId);
	}

	/**
	 * Updates the operational status of a file based on the provided information.
	 *
	 * @param ebsFileVo an instance of EbsFileVo containing the file details to be updated
	 * @return an integer representing the result of the update operation
	 */
	@Override
	public int updateFileOpbYn(EbsFileVo ebsFileVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		return applyMapper.updateFileOpbYn(ebsFileVo, userId);
	}

	/**
	 * Retrieves a list of ApplyVo objects based on the provided parameters.
	 *
	 * @param param a HashMap containing key-value pairs used as filtering criteria for selecting the bills
	 * @return a list of ApplyVo objects that match the specified criteria
	 */
	@Override
	public List<ApplyVo> selectBillAll(HashMap<String, Object> param) {
		return applyMapper.selectBillAll(param);
	}

	/**
	 * Creates a bill for home applications by inserting the initial data,
	 * updating necessary fields, and saving it to the database.
	 *
	 * @param applyVo the ApplyVo object that contains the details of the bill to be created.
	 *                This includes relevant information such as status and ID fields.
	 * @return the updated ApplyVo object with the generated ID and any additional changes applied.
	 */
	@Transactional
	@Override
	public ApplyVo createBillHome(ApplyVo applyVo) {
		String modId = new SecurityInfoUtil().getAccountId();
		applyVo.setStatus(true);
		homePageMapper.insertHomeLaws(applyVo);

		String sclDscRcpNmb = String.valueOf(applyVo.getId());
		applyVo.setSclDscRcpNmb(sclDscRcpNmb);
		applyVo.setModId(modId);

		applyMapper.updateBillHome(applyVo);

		return applyVo;
	}

	/**
	 * Stops the billing home process for the provided application details.
	 *
	 * @param applyVo the object containing application details, including the unique
	 *                identifier and other necessary information to stop the billing process
	 */
	@Override
	public void stopBillHome(ApplyVo applyVo) {

		String sclDscRcpNmb = applyVo.getSclDscRcpNmb();
		Long id = Long.valueOf(sclDscRcpNmb);

		Map<String, Object> map = new HashMap<>();
		map.put("updatedBy", new SecurityInfoUtil().getAccountId());
		map.put("status", false);
		map.put("id", id);
		homePageMapper.updateLaws(map);

		applyVo.setSclDscRcpNmb("stop");
		applyMapper.updateBillHome(applyVo);
	}

	/**
	 * Creates and inserts a new comment into the data source.
	 *
	 * @param commentsVo an instance of CommentsVo containing the details of the comment to be created
	 */
	@Override
	public void createComments(CommentsVo commentsVo) {
		homePageMapper.insertComments(commentsVo);
	}
}