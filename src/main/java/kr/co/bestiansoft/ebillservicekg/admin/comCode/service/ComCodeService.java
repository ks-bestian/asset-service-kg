package kr.co.bestiansoft.ebillservicekg.admin.comCode.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;

public interface ComCodeService {
    List<ComCodeVo> getGrpCodeList(HashMap<String, Object> param);
    List<ComCodeVo> getCodeList(HashMap<String, Object> param);
    ComCodeVo getGrpCodeById(Integer grpCode);
    ComCodeDetailVo getComCodeById(String codeId);
    ComCodeVo createGrpCode(ComCodeVo comCodeVo);
    ComCodeDetailVo createComCode(ComCodeDetailVo comCodeDetailVo);
    int updateGrpCode(ComCodeVo comCodeVo);
    int updateComCode(ComCodeDetailVo comCodeDetailVo);
    void deleteGrpCode(List<Long> grpCodes);
    void deleteComCode(List<String> codeIds, int grpCode);
}
