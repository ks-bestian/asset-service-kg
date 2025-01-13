package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;

@Mapper
public interface AgreeMapper {

	List<AgreeVo> selectAgreeList(HashMap<String, Object> param);

	AgreeVo selectAgreeDetail(String billId, String userId, String lang);

	List<AgreeVo> selectAgreeProposerList(String billId);

	int updateBillAgree(HashMap<String, Object> param);

}
