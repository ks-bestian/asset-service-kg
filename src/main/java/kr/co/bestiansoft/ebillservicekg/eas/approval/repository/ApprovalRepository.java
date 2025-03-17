package kr.co.bestiansoft.ebillservicekg.eas.approval.repository;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalRepository {
    int insertApproval(ApprovalVo vo);
}
