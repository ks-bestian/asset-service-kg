package kr.co.bestiansoft.ebillservicekg.admin.ccof.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;


@Mapper
public interface CcofMapper {
    List<CcofVo> selectListCcof(HashMap<String, Object> param);
    int insertCcofInUser(UserVo userVo);
    int updateCcof(UserVo ccofVo);
    void deleteCcof(String userId);
    void deleteCmit(String deptCd, String userId);
}
