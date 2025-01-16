package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface ApplyMapper {

	int insertApplyBill(ApplyVo applyVo);

	List<ApplyVo> selectListApply(HashMap<String, Object> param);

	int updateApplyByBillId(ApplyVo applyVo);

	int deleteApplyByBillId(String billId);

	int insertProposerList(ApplyVo applyVo);

	ApplyVo selectApplyDetail(String billId, String lang);

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

	List<EbsFileVo> selectApplyFileList(String billId);

	int updateFileDelete(EbsFileVo ebsFileVo, String userId);

}
