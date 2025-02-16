package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;

@Mapper
public interface BillMngMapper {

	List<BillMngVo> selectListBillMng(HashMap<String, Object> param);
	BillMngVo selectOneBill(BillMngVo argVo);


	void insertBillDetail(BillMngVo billMngVo);
	List<BillMngVo> selectListBillEtcInfo(BillMngVo billMngVo);

	//List<BillMngVo> selectListlegalReview(HashMap<String, Object> param);
	//BillMngVo selectOnelegalReview(BillMngVo argVo);
	List<BillMngVo> selectListCmtReviewReport(HashMap<String, Object> param);




/////////////////////////////





    List<ProposerVo> selectProposerMemberList (HashMap<String, Object> param);
    List<BillMngVo> selectCmtList (HashMap<String, Object> param);

    void insertBill(BillMngVo billMngVo);
    void insertProposers(ProposerVo proposerVo);

    List<ProposerVo> selectMemberList (HashMap<String, Object> param);

}
