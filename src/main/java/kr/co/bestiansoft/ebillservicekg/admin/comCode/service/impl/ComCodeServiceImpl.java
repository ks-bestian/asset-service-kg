package kr.co.bestiansoft.ebillservicekg.admin.comCode.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.repository.ComCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ComCodeServiceImpl implements ComCodeService {

    private final ComCodeMapper comCodeMapper;

    @Override
    public List<ComCodeVo> getGrpCodeList(HashMap<String, Object> param) {
        return comCodeMapper.selectListGrpCode(param);
    }

    @Override
    public List<ComCodeVo> getCodeList(HashMap<String, Object> param) {
        return comCodeMapper.selectListComCode(param);
    }

    @Override
    public ComCodeVo getGrpCodeById(Integer grpCode) {
        return comCodeMapper.selectGrpCode(grpCode);
    }

    @Override
    public ComCodeDetailVo getComCodeById(String codeId) {
        return comCodeMapper.selectComCode(codeId);
    }

    @Override
    public ComCodeVo createGrpCode(ComCodeVo comCodeVo) {
        comCodeMapper.insertGrpCode(comCodeVo);
        return comCodeVo;
    }

    @Override
    public ComCodeDetailVo createComCode(ComCodeDetailVo comCodeDetailVo) {
        comCodeMapper.insertComCode(comCodeDetailVo);
        return comCodeDetailVo;
    }

    @Override
    public int updateGrpCode(ComCodeVo comCodeVo) {
        return comCodeMapper.updateGrpCode(comCodeVo);
    }

    @Override
    public int updateComCode(ComCodeDetailVo comCodeDetailVo) {
        return comCodeMapper.updateComCode(comCodeDetailVo);
    }

    @Override
    public void deleteGrpCode(List<Long> grpCodes) {
        for(Long grpCode : grpCodes) {
            comCodeMapper.deleteGrpCode(grpCode);
        }
    }

    @Override
    public void deleteComCode(List<String> codeIds) {
        for(String codeId : codeIds) {
            comCodeMapper.deleteComCode(codeId);
        }
    }
}
