package kr.co.bestiansoft.ebillservicekg.test.repository2;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.test.vo.TestVo;

@Mapper
public interface TestMapper {
	void insertTest(TestVo testVo);
	List<TestVo> selectTest(TestVo testVo);


	void insertHomeLaws(ApplyVo applyVo);

	List<ApplyVo> selectBillCommentList(String lawId);
}
