package kr.co.bestiansoft.ebillservicekg.admin.comCode.service;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;

import java.util.HashMap;
import java.util.List;

public interface ComCodeService {
    List<ComCodeVo> getGrpCodeList(HashMap<String, Object> param);
    List<ComCodeVo> getCodeList(HashMap<String, Object> param);
    ComCodeVo getGrpCodeById(String grpCode);
    ComCodeVo getComCodeById(String codeId);
    ComCodeVo createGrpCode(ComCodeVo comCodeVo);
    ComCodeDetailVo createComCode(ComCodeDetailVo comCodeDetailVo);
    int updateGrpCode(ComCodeVo comCodeVo);
    int updateComCode(ComCodeDetailVo comCodeDetailVo);
    void deleteGrpCode(Integer grpCode);
    void deleteComCode(String codeId);
}
