package kr.co.bestiansoft.ebillservicekg.admin.ccof.repository;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;


@Mapper
public interface CcofMapper {
    List<CcofVo> getCcofList(HashMap<String, Object> param, String userId);
    int insertCcofInUser(CcofVo ccofVo);
    void deleteCcofInUser(HashMap<String, Object> param);
}
