package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ApplyServiceImpl implements ApplyService {
	
	private final ApplyMapper applyMapper;
	private final AgreeMapper agreeMapper;
	
	@Transactional
	@Override
	public ApplyVo createApply(ApplyVo applyVo) {
	//TODO :: 메세지 알람 적용해야함 
	
		//안건등록
		String billId = StringUtil.getEbillId();
		applyVo.setBillId(billId);
		applyMapper.insertApplyBill(applyVo);
		
		//발의자 요청
		List<String> proposerList = applyVo.getProposerList();
		String ppsrId = applyVo.getPpsrId();
	    proposerList.add(ppsrId);
		
		int ord = proposerList.size();
		for(String memberId : proposerList) {
			ApplyVo member = applyMapper.getProposerInfo(memberId);
			
			applyVo.setOrd(++ord);
			applyVo.setPolyCd(member.getPolyCd());
			applyVo.setPolyNm(member.getPolyNm());
			applyVo.setPpsrId(member.getMemberId());
			if(member.getMemberId().equals(ppsrId)) {
				applyVo.setSignDt("sign");
			}
			applyMapper.insertProposerList(applyVo);
		}
		
		return applyVo;
	}

	@Override
	public List<ApplyVo> getApplyList(HashMap<String, Object> param) {
		// TODO :: 대수 검색조건 설정 필요(현재 14로 하드코딩)
		return applyMapper.getApplyList(param);
	}

	@Transactional
	@Override
	public int updateApply(ApplyVo applyVo, String billId) {
		//TODO :: 
		//1. 수정이 가능 또는 불가능한 항목 정의 필요!
		//2. 메세지 알림 기능 적용
		
		applyVo.setBillId(billId);
		
		List<String> newProposerList = applyVo.getProposerList();
		List<String> proposerList = applyMapper.getProposerList(billId);
		
		int ord = proposerList.size();
		
		Set<String> allMembers = new HashSet<>(proposerList); // 기존 멤버들
		allMembers.addAll(newProposerList); // 모든 멤버를 합침

		for (String ppsrId : allMembers) {
		    if (proposerList.contains(ppsrId) && !newProposerList.contains(ppsrId)) {
		        // 삭제: 기존에 있었지만 새로운 리스트에 없는 경우
		        applyMapper.deleteProposerByPpsrId(ppsrId);
		    } else if (!proposerList.contains(ppsrId) && newProposerList.contains(ppsrId)) {
		        // 추가: 새로운 리스트에만 있는 경우
		    	ApplyVo member = applyMapper.getProposerInfo(ppsrId);
		    	
		        applyVo.setOrd(++ord);
				applyVo.setPolyCd(member.getPolyCd());
				applyVo.setPolyNm(member.getPolyNm());
				applyVo.setPpsrId(member.getMemberId());
				applyMapper.insertProposerList(applyVo);
		    }
		}
		
		return applyMapper.updateApplyByBillId(applyVo);
	}

	@Transactional
	@Override
	public int deleteApply(String billId) {
		applyMapper.deleteProposerByBillId(billId);
		return applyMapper.deleteApplyByBillId(billId);
	}

	@Override
	public ApplyResponse getApplyDetail(String billId) {
		ApplyResponse result = new ApplyResponse();
		
		//안건 상세
		ApplyVo applyDetail = applyMapper.getApplyDetail(billId);
		result.setApplyDetail(applyDetail);
		
		//발의자 대상
		List<AgreeVo> proposerList = agreeMapper.getAgreeProposerList(billId);
		result.setProposerList(proposerList);
		
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
	
}