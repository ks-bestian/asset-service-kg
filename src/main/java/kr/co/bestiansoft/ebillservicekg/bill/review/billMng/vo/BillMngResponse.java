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
    private BillMngVo billMngVo;//Agenda
    private BillMngVo billlegalReviewVo;//Legal review
    private BillMngVo billLangReviewVo;//Committee language specialty part
    private List<BillMngVo> billLangReviewVoList;//Committee language specialty part

    private BillMngVo billCmtReviewVo;//Committee
    private List<BillMngVo> billCmtReviewVoList;//Committee
    private ProcessVo processVo;

    private List<BillMngVo> billEtcInfoList;
    private List<EbsFileVo> fileList;
    private List<MtngAllVo> cmtMeetingList;


}
