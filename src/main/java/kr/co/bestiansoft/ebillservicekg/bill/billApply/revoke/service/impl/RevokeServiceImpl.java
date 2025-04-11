package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository.RevokeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
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
	private final ComFileService comFileService;
	private final BillMngService billMngService;

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

		BillMngVo billMngVo = new BillMngVo();
		billMngVo.setBillId(billId);
		billMngVo.setRmrkKg(vo.getWtCnKg());
		billMngVo.setRmrkRu(vo.getWtCnRu());
		billMngVo.setFiles(vo.getFiles());
		billMngVo.setClsCd("400"); //의안철회
		billMngVo.setFileKindCd("170"); //안건철회문서
		billMngService.insertBillDetail(billMngVo);
		
//		this.updateRevoke(billId, vo);
//		if(vo.getFiles() != null) {
//			String[] fileKindCd = new String[vo.getFiles().length];
//			for(int i = 0; i < vo.getFiles().length; ++i) {
//				fileKindCd[i] = "170"; //안건철회
//			}
//			comFileService.saveFileEbs(vo.getFiles(), fileKindCd, billId);	
//		}
		
		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("1100"); //안건철회관리
//		pVo.setTaskId(vo.getTaskId());
		processService.handleProcess(pVo);

		return pVo;
	}
	
	@Transactional
	@Override
	public ProcessVo billRevokeSubmit(String billId,RevokeVo vo) {

		ProcessVo pVo = new ProcessVo();
		pVo.setBillId(billId);
		pVo.setStepId("1150"); //안건철회관리
		processService.handleProcess(pVo);

		return pVo;
	}

	@Override
	public int billRevokeCancle(String billId, HashMap<String, Object> param) {
		processService.undoProcess(billId, "1100");
		
		EbsFileVo ebsFileVo = new EbsFileVo();
		ebsFileVo.setBillId(billId);
		ebsFileVo.setFileKindCd("170"); //안건철회
		ebsFileVo.setModId(new SecurityInfoUtil().getAccountId());
		billMngService.updateEbsFileDelYn(ebsFileVo);
		
		return 0;
	}

	@Override
	public int updateRevoke(String billId, RevokeVo revokeVo) {
		revokeVo.setBillId(billId);
		revokeVo.setModId(new SecurityInfoUtil().getAccountId());
		return revokeMapper.updateRevoke(revokeVo);
	}

}