package kr.co.bestiansoft.ebillservicekg.test.repository2;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;

@Mapper
public interface HomePageMapper {
	
	void insertHomeLaws(ApplyVo applyVo);
	
	List<ApplyVo> selectBillCommentList(String lawId);
	
}
