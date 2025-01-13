package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;

@Mapper
public interface RevokeMapper {

	List<RevokeVo> selectRevokeList(HashMap<String, Object> param);

	RevokeVo getRevokeDetail(String billId);

	List<RevokeVo> getProposerList(String billId);

	int updateRevokeRequset(HashMap<String, Object> param);

	int updateRevokeCancle(HashMap<String, Object> param);

	int updateRevoke(RevokeVo revokeVo);

}
