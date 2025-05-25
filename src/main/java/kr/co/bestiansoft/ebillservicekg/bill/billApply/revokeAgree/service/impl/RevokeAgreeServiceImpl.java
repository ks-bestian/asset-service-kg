package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.repository.RevokeAgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.service.RevokeAgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo.RevokeAgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo.RevokeAgreeVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RevokeAgreeServiceImpl implements RevokeAgreeService {
	
	private final RevokeAgreeMapper revokeAgreeMapper;
	private final ApplyMapper applyMapper;

	@Override
	public List<ApplyVo> getRevokeAgreeList(HashMap<String, Object> param) {
		String userId = new SecurityInfoUtil().getAccountId();
//		param.put("userId", userId);
//		return revokeAgreeMapper.selectRevokeAgreeList(param);
		param.put("loginId", userId);
		return applyMapper.selectListBillRevokeAgree(param);
	}
	
//	@Override
//	public List<RevokeAgreeVo> getRevokeAgreeList(HashMap<String, Object> param) {
//		String userId = new SecurityInfoUtil().getAccountId();
//		param.put("userId", userId);
//		return revokeAgreeMapper.selectRevokeAgreeList(param);
//	}

	@Override
	public RevokeAgreeResponse getRevokeDetail(String billId, HashMap<String, Object> param) {
		RevokeAgreeResponse result = new RevokeAgreeResponse();
		String userId = new SecurityInfoUtil().getAccountId();
		
		param.put("userId", userId);
		param.put("billId", billId);
		RevokeAgreeVo revokeAgreeDetail = revokeAgreeMapper.getRevokeAgreeDetail(param);
		result.setRevokeAgreeDetail(revokeAgreeDetail);
		
		List<RevokeAgreeVo> proposerList = revokeAgreeMapper.getProposerList(param);
		result.setProposerList(proposerList);
		
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(param);
		result.setFileList(fileList);
		
		return result;
	}

	@Override
	public int updateRevokeAgree(String billId, HashMap<String, Object> param) {
		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		param.put("billId", billId);
		return revokeAgreeMapper.updateRevokeAgree(param);
	}


}