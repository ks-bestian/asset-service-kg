package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProcessVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;

@Mapper
public interface BillMngMapper {
    List<BillMngVo> getBillList (HashMap<String, Object> param);
    
    BillMngVo getBillById(HashMap<String, Object> param);
    List<ProposerVo> selectProposerMemberList (HashMap<String, Object> param);
    List<BillMngVo> selectCmtList (HashMap<String, Object> param);
    
    void insertBill(BillMngVo billMngVo);
    void insertProposers(ProposerVo proposerVo);
    void insertProcess(ProcessVo processVo);
    void updateProcess(ProcessVo processVo);
    List<ProposerVo> selectMemberList (HashMap<String, Object> param);
    
}
