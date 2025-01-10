package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AgreeServiceImpl implements AgreeService {
	
	private final AgreeMapper agreeMapper;
	
	@Override
	public List<AgreeVo> getAgreeList(HashMap<String, Object> param) {
		
		return agreeMapper.getAgreeList(param);
	}

	@Override
	public AgreeResponse getAgreeDetail(String billId, String userId) {
		
		AgreeResponse result = new AgreeResponse();
		
		//동의 상세
		AgreeVo agreeDetail = agreeMapper.getAgreeDetail(billId, userId);
		result.setAgreeDetail(agreeDetail);
		
		//동의서명 목록
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);
		
		
		return result;
	}

	@Transactional
	@Override
	public int setBillAgree(String billId, HashMap<String, Object> param) {
		param.put("billId", billId);
		return agreeMapper.setBillAgree(param);
	}

}