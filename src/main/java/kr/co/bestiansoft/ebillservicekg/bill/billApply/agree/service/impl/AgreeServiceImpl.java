package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ArrayNode;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeResponse;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository.ApplyMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.test.domain.CommentsHierarchy;
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

	/**
	 * Retrieves a list of ApplyVo objects for agreements based on the provided parameters.
	 * The method automatically includes the login ID of the current user in the parameters
	 * before querying the data.
	 *
	 * @param param a HashMap containing the search parameters. The method will add the
	 *              current user's login ID to this map under the key "loginId".
	 * @return a List of ApplyVo objects representing the agreement information that matches
	 *         the search criteria specified in the parameters.
	 */
	@Override
	public List<ApplyVo> getAgreeList(HashMap<String, Object> param) {
		param.put("loginId", new SecurityInfoUtil().getAccountId());
		return applyMapper.selectListBillAgree(param);
//		return agreeMapper.selectAgreeList(param);
	}

	/**
	 * Retrieves the agreement details for a given bill ID and language.
	 * This method fetches the following:
	 * - Agreement particulars for the given bill ID and user.
	 * - The list of proposers for the agreement.
	 * - A list of associated files.
	 * - A hierarchical structure of comments related to the agreement.
	 *
	 * @param billId the unique identifier of the bill whose agreement details are to be retrieved
	 * @param lang   the language preference for retrieving agreement details
	 * @return an AgreeResponse object containing the detailed agreement information, including
	 *         proposers, associated files, and comments
	 */
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

	/**
	 * Updates the agreement status of a bill based on the provided parameters.
	 * The method automatically appends the user's ID and the bill's ID to the input parameters
	 * before invoking the data update operation.
	 *
	 * @param billId the unique identifier of the bill to update agreement status for
	 * @param param  a HashMap containing additional parameters for updating the bill agreement.
	 *               The method will inject the user's ID using the current session and the bill ID
	 *               into this map before proceeding with the update.
	 * @return an integer representing the number of rows affected by the update in the database
	 */
	@Transactional
	@Override
	public int setBillAgree(String billId, HashMap<String, Object> param) {

		String userId = new SecurityInfoUtil().getAccountId();
		param.put("userId", userId);
		param.put("billId", billId);

		return agreeMapper.updateBillAgree(param);
	}

}