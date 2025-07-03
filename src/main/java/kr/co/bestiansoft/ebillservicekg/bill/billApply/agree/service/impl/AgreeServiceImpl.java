package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.impl;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.test.domain.CommentsHierarchy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.test.repository2.HomePageMapper;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AgreeServiceImpl implements AgreeService {

	private final AgreeMapper agreeMapper;
	private final ApplyMapper applyMapper;
	private final HomePageMapper homePageMapper;

	@Override
	public List<ApplyVo> getAgreeList(HashMap<String, Object> param) {
		param.put("loginId", new SecurityInfoUtil().getAccountId());
		return applyMapper.selectListBillAgree(param);
//		return agreeMapper.selectAgreeList(param);
	}

	@Override
	public AgreeResponse getAgreeDetail(String billId, String lang) {

		AgreeResponse result = new AgreeResponse();

		//agreement particular
		String userId = new SecurityInfoUtil().getAccountId();
		AgreeVo agreeDetail = agreeMapper.selectAgreeDetail(billId, userId, lang);
		result.setAgreeDetail(agreeDetail);

		//Signature inventory
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);

		//File list
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", billId);
		param.put("lang", lang);
		List<EbsFileVo> fileList = applyMapper.selectApplyFileList(param);
		result.setFileList(fileList);
		
		List<CommentsVo> commentList = homePageMapper.selectCommentsByLawId(null);

		CommentsHierarchy ch = new CommentsHierarchy();
		ch.buildCommentsHierarchy(commentList);
		ArrayNode nodes = ch.getCommentsJson();
		result.setCommentLists(nodes);

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