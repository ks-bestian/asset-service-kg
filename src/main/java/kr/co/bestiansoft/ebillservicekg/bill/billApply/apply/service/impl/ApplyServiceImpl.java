package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import kr.co.bestiansoft.ebillservicekg.test.repository2.TestMapper;
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
	private final TestMapper testHomeMapper;
	
	@Transactional
	@Override
	public ApplyVo createApply(ApplyVo applyVo) {
	//TODO :: 메세지 알람 적용해야함

		//사회토론번호
		applyMapper.insertHomeLaws(applyVo);
		applyVo.setSclDscRcpNmb(String.valueOf(applyVo.getId()));

		//안건등록
		String billId = StringUtil.getEbillId();
		applyVo.setBillId(billId);
		String ppsrId = new SecurityInfoUtil().getAccountId();
		applyVo.setPpsrId(ppsrId);
		applyMapper.insertApplyBill(applyVo);

		if (applyVo.getCmtCd() != null && !applyVo.getCmtCd().isEmpty()) {
			applyMapper.insertApplyCmt(applyVo);
		}

		//파일등록
		comFileService.saveFileEbs(applyVo.getFiles(), applyVo.getFileKindCds(), billId);
		//파일 정보를 가지고 있어서 null처리
		applyVo.setFiles(null);

		//발의자 요청
		List<String> proposerList = applyVo.getProposerList();
	    proposerList.add(applyVo.getPpsrId());

		int ord = proposerList.size();
		for(String memberId : proposerList) {
			ApplyVo member = applyMapper.getProposerInfo(memberId);

			if(member == null) break;
			
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

	@Override
	public List<ApplyVo> getApplyList(HashMap<String, Object> param) {
		String loginId = new SecurityInfoUtil().getAccountId();
		param.put("loginId", loginId);
		return applyMapper.selectListApply(param);
	}

	@Transactional
	@Override
	public int updateApply(ApplyVo applyVo, String billId) {
		//TODO :: 1. 메세지 알림 기능 적용 필요
		String loginId = new SecurityInfoUtil().getAccountId();

		//발의자 변경
		applyVo.setBillId(billId);

		List<String> newProposerList = applyVo.getProposerList();
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

		//파일변경
		if (applyVo.getFiles() != null) {
			comFileService.saveFileEbs(applyVo.getFiles(), applyVo.getFileKindCds(), billId);
			applyVo.setFiles(null);
		}

		if (applyVo.getCmtCd() != null && !applyVo.getCmtCd().isEmpty()) {
			applyMapper.updateApplyCmt(applyVo);
		}

		//bill update
		applyVo.setLoginId(loginId);
		return applyMapper.updateApplyByBillId(applyVo);
	}

	@Transactional
	@Override
	public int deleteApply(String billId) {
		applyMapper.deleteProposerByBillId(billId);
		return applyMapper.deleteApplyByBillId(billId);
	}

	@Override
	public ApplyResponse getApplyDetail(String billId, String lang) {

		ApplyResponse result = new ApplyResponse();

		//안건 상세
		ApplyVo applyDetail = applyMapper.selectApplyDetail(billId, lang);
		result.setApplyDetail(applyDetail);

		//파일 리스트
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(billId);
		result.setFileList(fileList);

		//발의자 대상
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);


		//안건 프로세스정보가져오기.
		ProcessVo pcParam = new ProcessVo();
		pcParam.setBillId(billId);
		ProcessVo pcVo = processMapper.selectBpInstance(pcParam);
		result.setProcessVo(pcVo);

		return result;
	}

	@Transactional
	@Override
	public int applyBill(String billId) {

		String statCd = "ST010";
		String procId = StringUtil.getEbillProcId();
		String procKndCd = "PC010";

		applyMapper.insertBillProcess(billId, procId, procKndCd);

		return applyMapper.updateApplyBill(billId, statCd);
	}

	@Transactional
	@Override
	public int revokeBill(String billId, ApplyVo applyVo) {

		String statCd = "ST310";
		applyVo.setStatCd(statCd);
		applyVo.setBillId(billId);
		applyMapper.updateBillPpsrRevoke(applyVo);

		return applyMapper.updateRevokeBill(applyVo);
	}

	@Transactional
	@Override
	public int updateBillStatus(String billId, ApplyVo applyVo) {
		applyVo.setBillId(billId);
		return applyMapper.updateBillStatus(applyVo);
	}

	@Transactional
	@Override
	public ApplyVo saveBillAccept(String billId, ApplyVo applyVo) {


		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId(applyVo.getStepId());
		pVo.setTaskId(applyVo.getTaskId());
		processService.handleProcess(pVo);

		return applyVo;
	}

	@Override
	public int deleteBillFile(EbsFileVo ebsFileVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		return applyMapper.updateFileDelete(ebsFileVo, userId);
	}

	@Override
	public List<ApplyVo> selectBillAll(HashMap<String, Object> param) {
		return applyMapper.selectBillAll(param);
	}

}