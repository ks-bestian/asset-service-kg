package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AgreeServiceImpl implements AgreeService {

	private final AgreeMapper agreeMapper;
	private final ApplyMapper applyMapper;

	@Override
	public List<AgreeVo> getAgreeList(HashMap<String, Object> param) {
		param.put("loginId", new SecurityInfoUtil().getAccountId());
		return agreeMapper.selectAgreeList(param);
	}

	@Override
	public AgreeResponse getAgreeDetail(String billId, String lang) {

		AgreeResponse result = new AgreeResponse();

		//동의 상세
		String userId = new SecurityInfoUtil().getAccountId();
		AgreeVo agreeDetail = agreeMapper.selectAgreeDetail(billId, userId, lang);
		result.setAgreeDetail(agreeDetail);

		//동의서명 목록
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);

		//파일목록
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(billId);
		result.setFileList(fileList);

		return result;
	}

	@Transactional
	@Override
	public int setBillAgree(String billId, HashMap<String, Object> param) {

		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		param.put("billId", billId);

		return agreeMapper.updateBillAgree(param);
	}

}