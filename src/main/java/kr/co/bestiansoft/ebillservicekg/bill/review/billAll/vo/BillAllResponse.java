package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.Data;

@Data
public class BillAllResponse {

	private BillAllVo billBasicInfo;


    private List<AgreeVo> proposerList;
    private List<EbsFileVo> billFileList;

    private List<EbsFileVo> langFileList;

    private List<BillAllVo> cmtList;
    private List<MtngAllVo> cmtMtList;
    private List<MtngAllVo> mainMtList;
    private List<MtngAllVo> partyMtList;
    private List<BillAllVo> etcInfoList;

    private BillAllVo billlegalReviewVo;
    private List<BillAllVo> billLangReviewVoList;//Committee language specialty part
    private List<BillAllVo> billCmtReviewVoList;//Committee

}
