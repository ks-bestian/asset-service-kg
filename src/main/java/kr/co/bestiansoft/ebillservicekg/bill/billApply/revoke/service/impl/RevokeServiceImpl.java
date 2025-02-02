package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository.RevokeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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

	@Override
	public List<RevokeVo> getRevokeList(HashMap<String, Object> param) {
		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		return revokeMapper.selectRevokeList(param);
	}

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

		//파일목록
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(billId);
		result.setFileList(fileList);

		return result;
	}

	@Transactional
	@Override
	public ProcessVo billRevokeRequest(String billId,RevokeVo vo) {

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId(vo.getStepId());
		pVo.setTaskId(vo.getTaskId());
		processService.handleProcess(pVo);

		return pVo;
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