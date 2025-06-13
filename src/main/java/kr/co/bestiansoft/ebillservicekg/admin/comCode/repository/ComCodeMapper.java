package kr.co.bestiansoft.ebillservicekg.admin.comCode.repository;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ComCodeMapper {

    List<ComCodeVo> selectListGrpCode(HashMap<String, Object> param);
    List<ComCodeVo> selectListComCode(HashMap<String, Object> param);
    ComCodeVo selectGrpCode(Integer grpCode);
    ComCodeDetailVo selectComCode(String codeId);
    int insertGrpCode(ComCodeVo comCodeVo);
    int insertComCode(ComCodeDetailVo comCodeDetailVo);
    int updateGrpCode(ComCodeVo comCodeVo);
    int updateComCode(ComCodeDetailVo comCodeDetailVo);
    void deleteGrpCode(Long codeId);
    void deleteComCode(String codeId, int grpCode);
}
