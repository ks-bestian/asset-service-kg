package kr.co.bestiansoft.ebillservicekg.admin.baseCode.repository;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BaseCodeMapper {
    List<BaseCodeVo> selectListBaseCode(HashMap<String, Object> param);
    int insertBaseCode(BaseCodeVo baseCodeVo);
    int updateBaseCode(BaseCodeVo baseCodeVo);
    void deleteBaseCode(Long ids);
    BaseCodeVo selectBaseCode(Long seq);
}
