package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo.RevokeAgreeVo;

@Mapper
public interface RevokeAgreeMapper {

	List<RevokeAgreeVo> getRevokeAgreeList(HashMap<String, Object> param);

	RevokeAgreeVo getRevokeAgreeDetail(HashMap<String, Object> param);

	List<RevokeAgreeVo> getProposerList(HashMap<String, Object> param);

	int updateRevokeAgree(HashMap<String, Object> param);

}
