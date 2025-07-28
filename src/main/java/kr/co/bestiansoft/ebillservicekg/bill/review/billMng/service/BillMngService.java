package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

public interface BillMngService {
	/* Agenda management List screen */
    List<BillMngVo> getBillList(HashMap<String, Object> param);


    /* Agenda management Detailed screen */
    BillMngResponse selectOneBill(BillMngVo param);

    /* Agenda management Detailed screen - Agenda particular information, committee information, Voter information etc.*/
    BillMngResponse getBillById(HashMap<String, Object> param);


    /* Agenda - Agenda */
    BillMngVo billRegisterMng(BillMngVo billMngVo);

    /* Agenda - Committee */
    BillMngVo billCmtRegMng(BillMngVo billMngVo);



	/* Agenda management Other information list */
    BillMngResponse selectListBillEtcInfo(HashMap<String, Object> param);

	/* Agenda management Legal review List screen */
    //List<BillMngVo> selectListlegalReview(HashMap<String, Object> param);

    /* Agenda management Legal review particular screen */
    //BillMngResponse selectOnelegalReview(HashMap<String, Object> param);

    /* Agenda Other information registration */
    BillMngVo insertBillDetail(BillMngVo billMngVo) throws Exception;

    /* Agenda Legal review report */
    BillMngVo insertBillLegalReviewReport(BillMngVo billMngVo);


	/* Retrieve review reports of agenda committee, competent committee, and related committee. */
    List<BillMngVo> selectListCmtReviewReport(HashMap<String, Object> param);


    /* Bill meeting review */
    BillMngVo insertCmtMeetingRvReport(BillMngVo billMngVo) throws Exception;

    /* Bill meeting review */
    BillMngVo deleteCmtReview(BillMngVo billMngVo);


	/* List of agendas to be submitted to the plenary session. */
    List<BillMngVo> selectListMainMtSubmit(HashMap<String, Object> param);


    ////////////////////////////////////////////////////////////////////


    /* Agenda - Written registration screen */
    BillMngVo createBill(BillMngVo billMngVo);

    /* Agenda management proposer list.  */
    List<ProposerVo> selectProposerByBillId(HashMap<String, Object> param);

	BillMngVo insertBillCommitt(BillMngVo billMngVo);

//	EbsFileVo insertBillMngFile(EbsFileVo ebsfileVo);

	EbsFileVo updateEbsFileDelYn(EbsFileVo ebsFileVo);

	BillMngVo insertBillDetailFile(BillMngVo billMngVo) throws Exception;

	void deleteBillDetail(BillMngVo billMngVo);

	BillMngVo insertBillPrmg(BillMngVo billMngVo) throws Exception;

	BillMngVo presidentReject(BillMngVo billMngVo) throws Exception;
}
