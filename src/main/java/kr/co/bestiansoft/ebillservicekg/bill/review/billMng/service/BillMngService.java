package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngResponse;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;

public interface BillMngService {
	/* 안건 관리 리스트 화면 */
    List<BillMngVo> getBillList(HashMap<String, Object> param);

    /* 안건 관리 상세화면 - 안건 상세 정보, 위원회 정보, 발의자 정보 등*/
    BillMngResponse getBillById(BillMngVo param);


    /* 안건관리 - 안건접수 */
    BillMngVo billRegisterMng(BillMngVo billMngVo);

    /* 안건관리 - 위원회회부 */
    BillMngVo billCmtRegMng(BillMngVo billMngVo);




	/* 안건 관리 법률검토 리스트 화면 */
    List<BillMngVo> selectListlegalReview(HashMap<String, Object> param);

    /* 안건 관리 법률검토 상세 화면 */
    //BillMngResponse selectOnelegalReview(HashMap<String, Object> param);

    /* 안건관리 기타정보등록 */
    BillMngVo insertBillDetail(BillMngVo billMngVo);

    /* 안건관리 법률검토 보고 */
    BillMngVo insertBillLegalReviewReport(BillMngVo billMngVo);


    ////////////////////////////////////////////////////////////////////


    /* 안건관리 - 서면 등록 화면 */
    BillMngVo createBill(BillMngVo billMngVo);

    /* 안건관리 발의자목록  */
    List<ProposerVo> selectProposerByBillId(HashMap<String, Object> param);

	BillMngVo insertBillCommitt(BillMngVo billMngVo);

}
