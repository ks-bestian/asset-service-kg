package kr.co.bestiansoft.ebillservicekg.test.repository2;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;

@Mapper
public interface HomePageMapper {
	
	void insertHomeLaws(ApplyVo applyVo);
	
	List<ApplyVo> selectBillCommentList(String lawId);
	void updateLaws(Map<String, Object> map);
	List<CommentsVo> selectCommentsByLawId(Long lawId);
	void insertComments(CommentsVo commentsVo);
}
