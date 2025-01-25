package kr.co.bestiansoft.ebillservicekg.admin.billMng.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface SystemBillMapper {

	SystemBillVo selectBillDetail(HashMap<String, Object> param);

	int createBillDetail(SystemBillVo systemBillVo);

	List<EbsFileVo> selectBillFile(String billId);

	int createBillFile(EbsFileVo fileVo);

	int updateBillDetail(SystemBillVo systemBillVo);

	List<SystemBillVo> selectBillMtngList(HashMap<String, Object> param);

	List<EbsFileVo> selectMtngFile(HashMap<String, Object> param);

	void createMtngFile(EbsFileVo fileVo);

	void updateFileRmk(EbsFileVo fileVo);

	void createMasterCmt(SystemBillVo systemBillVo);

	List<EbsFileVo> selectBillFileByCmt(HashMap<String, Object> param);

	List<SystemBillVo> selectMtnBillList(HashMap<String, Object> param);

	List<EbsFileVo> selectBillFileMtn(String billId);

	List<SystemBillVo> selectBillRelationMtngList(HashMap<String, Object> param);

	List<SystemBillVo> selectBillGovermentList(HashMap<String, Object> param);

}
