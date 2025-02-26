package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.Data;

@Data
public class BillMngResponse {

	private List<ProposerVo> proposerList;
    private List<BillMngVo> cmtList;
    private BillMngVo billMngVo;//안건기본
    private BillMngVo billlegalReviewVo;//법률검토
    private BillMngVo billLangReviewVo;//위원회언어전문파트
    private List<BillMngVo> billLangReviewVoList;//위원회언어전문파트

    private BillMngVo billCmtReviewVo;//위원회심사
    private List<BillMngVo> billCmtReviewVoList;//위원회심사
    private ProcessVo processVo;

    private List<BillMngVo> billEtcInfoList;
    private List<EbsFileVo> fileList;
    private List<MtngAllVo> cmtMeetingList;


}
