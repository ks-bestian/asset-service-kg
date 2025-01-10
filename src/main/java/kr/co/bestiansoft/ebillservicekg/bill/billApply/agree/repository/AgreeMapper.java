package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;

@Mapper
public interface AgreeMapper {

	List<AgreeVo> getAgreeList(HashMap<String, Object> param);

	AgreeVo getAgreeDetail(String billId, String userId);

	List<AgreeVo> selectAgreeProposerList(String billId);

	int setBillAgree(HashMap<String, Object> param);

}
