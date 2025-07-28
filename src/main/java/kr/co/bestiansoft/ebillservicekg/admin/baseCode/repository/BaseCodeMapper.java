package kr.co.bestiansoft.ebillservicekg.admin.baseCode.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;

@Mapper
public interface BaseCodeMapper {
    List<BaseCodeVo> selectListBaseCode(HashMap<String, Object> param);
    int insertBaseCode(BaseCodeVo baseCodeVo);
    int updateBaseCode(BaseCodeVo baseCodeVo);
    void deleteBaseCode(String codeId);
    BaseCodeVo selectBaseCode(Long seq);
}
