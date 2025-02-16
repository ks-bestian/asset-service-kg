package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.Data;

@Data
public class BillMngResponse {

	private List<ProposerVo> proposerList;
    private List<BillMngVo> cmtList;
    private BillMngVo billMngVo;//안건기본
    private BillMngVo billlegalReviewVo;//법률검토
    private BillMngVo billLangReview1stVo;//위원회1차언어전문파트
    private BillMngVo billLangReview2stVo;//위원회2차언어전문파트
    private ProcessVo processVo;

    private List<BillMngVo> billEtcInfoList;
    private List<EbsFileVo> fileList;




}
