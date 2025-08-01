package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
import lombok.Data;

@Data
public class ApplyResponse {

	ApplyVo applyDetail;

	List<ApplyVo> applyList;

	List<AgreeVo> proposerList;

	List<EbsFileVo> fileList;

	List<EbsFileVo> applyFileList;

	ProcessVo processVo;

//	List<ApplyVo> commentList;

	List<CommentsVo> commentList;
	ArrayNode commentLists;
}
