package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.DateFormatter;

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
	//todo :: 
	//1.멤버 정보 필요!!
	//2.메세지 알람 적용
	
		//안건등록
		String billId = StringUtil.getEbillId();
		applyVo.setBillId(billId);
		applyMapper.insertApplyBill(applyVo);
		
		//발의자 요청
		List<String> proposerList = applyVo.getProposerList();
		String ppsrId = applyVo.getPpsrId();
	    proposerList.add(ppsrId);
		
		int ord = proposerList.size();
		for(String member : proposerList) {
			applyVo.setOrd(++ord);
			applyVo.setPolyCd("11");
			applyVo.setPolyNm("22");
			applyVo.setPpsrId(member);
			if(member.equals(ppsrId)) {
				applyVo.setSignDt("sign");
			}
			applyMapper.insertProposerList(applyVo);
		}
		
		return applyVo;
	}

	@Override
	public List<ApplyVo> getApplyList(HashMap<String, Object> param) {
		return applyMapper.getApplyList(param);
	}

	@Transactional
	@Override
	public int updateApply(ApplyVo applyVo, String billId) {
		//todo :: 
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
		        applyVo.setOrd(++ord);
				applyVo.setPolyCd("11");
				applyVo.setPolyNm("22");
				applyVo.setPpsrId(ppsrId);
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
		
		String statCd = "ST100";
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