package kr.co.bestiansoft.ebillservicekg.admin.comCode.repository;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ComCodeMapper {

    List<ComCodeVo> getGrpCodeList(HashMap<String, Object> param);
    List<ComCodeVo> getComCodeList(HashMap<String, Object> param);
    ComCodeVo getGrpCodeById(String grpCode);
    ComCodeVo getComCodeById(String codeId);
    int insertGrpCode(ComCodeVo comCodeVo);
    int insertComCode(ComCodeDetailVo comCodeDetailVo);
    int updateGrpCode(ComCodeVo comCodeVo);
    int updateComCode(ComCodeDetailVo comCodeDetailVo);
    void deleteGrpCode(Integer codeId);
    void deleteComCode(String codeId);
    boolean existCodeIdInGrp(Integer id);
    boolean existCodeId(String codeId);
    Integer createGrpId();
}
