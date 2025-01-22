package kr.co.bestiansoft.ebillservicekg.admin.billMng.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface SystemBillMapper {

	SystemBillVo selectBillDetail(String billId, String lang);

	int createBillDetail(SystemBillVo systemBillVo);

	List<EbsFileVo> selectOpinionFile(String billId);

	int createBillFile(EbsFileVo fileVo);
	
}
