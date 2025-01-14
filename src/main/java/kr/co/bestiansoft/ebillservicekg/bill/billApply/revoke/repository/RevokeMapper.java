package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;

@Mapper
public interface RevokeMapper {

	List<RevokeVo> selectRevokeList(HashMap<String, Object> param);

	RevokeVo selectRevokeDetail(HashMap<String, Object> param);

	List<RevokeVo> selectProposerList(HashMap<String, Object> param);

	int updateRevokeRequset(HashMap<String, Object> param);

	int updateRevokeCancle(HashMap<String, Object> param);

	int updateRevoke(RevokeVo revokeVo);

}
