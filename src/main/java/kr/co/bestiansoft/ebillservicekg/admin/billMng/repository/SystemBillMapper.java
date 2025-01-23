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

	List<EbsFileVo> selectOpinionFile(String billId);

	int createBillFile(EbsFileVo fileVo);

	int updateBillDetail(SystemBillVo systemBillVo);
	
}
