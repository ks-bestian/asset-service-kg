package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository.RevokeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RevokeServiceImpl implements RevokeService {
	
	private final RevokeMapper revokeMapper;

	@Override
	public List<RevokeVo> getRevokeList(HashMap<String, Object> param) {
		// TODO :: 로그인한 계정 아이디 필요, 철회 코드정의 필요, 대수 검색조건 설정 필요(현재 14로 하드코딩)
		return revokeMapper.getRevokeList(param);
	}

	@Override
	public RevokeResponse getRevokeDetail(String billId) {
		RevokeResponse result = new RevokeResponse();
		
		RevokeVo revokeDetail = revokeMapper.getRevokeDetail(billId);
		result.setRevokeDetail(revokeDetail);
		
		List<RevokeVo> proposerList = revokeMapper.getProposerList(billId);
		result.setProposerList(proposerList);
		
		return result;
	}

	@Transactional
	@Override
	public int billRevokeRequest(String billId, HashMap<String, Object> param) {
		param.put("billId", billId);
		param.put("statCd", "ST031");
		return revokeMapper.updateRevokeRequset(param);
	}

	@Override
	public int billRevokeCancle(String billId, HashMap<String, Object> param) {
		param.put("billId", billId);
		param.put("statCd", "ST030");
		return revokeMapper.updateRevokeCancle(param);
	}

	@Override
	public int updateRevoke(String billId, RevokeVo revokeVo) {
		revokeVo.setBillId(billId);
		return revokeMapper.updateRevoke(revokeVo);
	}

}