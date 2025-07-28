package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface ApplyMapper {

	int insertApplyBill(ApplyVo applyVo);

	List<ApplyVo> selectListBillApply(HashMap<String, Object> param);

	List<ApplyVo> selectListBillAgree(HashMap<String, Object> param);

	List<ApplyVo> selectListBillRevoke(HashMap<String, Object> param);

	List<ApplyVo> selectListBillRevokeAgree(HashMap<String, Object> param);

	ApplyVo selectBill(HashMap<String, Object> param);

//	List<ApplyVo> selectListApply(HashMap<String, Object> param);

	int updateApplyByBillId(ApplyVo applyVo);

	int deleteApplyByBillId(String billId);

	int insertProposerList(ApplyVo applyVo);

	ApplyVo selectApplyDetail(HashMap<String, Object> param);

	void deleteProposerByBillId(String billId);

	int updateApplyBill(String billId, String statCd);

	int updateRevokeBill(ApplyVo applyVo);

	int updateBillStatus(ApplyVo applyVo);

	List<String> getProposerList(String billId);

	void deleteProposerByPpsrId(String ppsrId);

	void updateBillPpsrRevoke(ApplyVo applyVo);

	ApplyVo getProposerInfo(String memberId);

	void insertBillProcess(String billId, String procId, String procKndCd);

	int insertHomeLaws(ApplyVo applyVo);

	List<EbsFileVo> selectBillFileList(HashMap<String, Object> param);

	List<EbsFileVo> selectApplyFileList(HashMap<String, Object> param);

	int updateFileDelete(EbsFileVo ebsFileVo, String userId);

	int deleteBillFileByBillId(HashMap<String, Object> param);

	int updateFileOpbYn(EbsFileVo ebsFileVo, String userId);

	List<ApplyVo> selectBillAll(HashMap<String, Object> param);

	void updateBillHome(ApplyVo applyVo);

	void updateBillRecptnDt(ApplyVo applyVo);


}
