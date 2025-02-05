package kr.co.bestiansoft.ebillservicekg.admin.ccof.repository;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;


@Mapper
public interface CcofMapper {
    List<CcofVo> selectListCcof(HashMap<String, Object> param);
    int insertCcofInUser(UserVo userVo);
    int updateCcof(UserVo ccofVo);
    void deleteCcof(String userId);
}
