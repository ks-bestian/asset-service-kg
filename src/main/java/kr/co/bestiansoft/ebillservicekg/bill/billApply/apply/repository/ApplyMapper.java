package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;

@Mapper
public interface ApplyMapper {

	int insertApplyBill(ApplyVo applyVo);

	List<ApplyVo> getApplyList(HashMap<String, Object> param);

	int updateApplyByBillId(ApplyVo applyVo);

	int deleteApplyByBillId(String billId);

	int insertProposerList(ApplyVo applyVo);

	ApplyVo getApplyDetail(String billId);

	void deleteProposerByBillId(String billId);

	int updateApplyBill(String billId, String statCd);

	int updateRevokeBill(ApplyVo applyVo);

	int updateBillStatus(ApplyVo applyVo);

	List<String> getProposerList(String billId);

	void deleteProposerByPpsrId(String ppsrId);

}
