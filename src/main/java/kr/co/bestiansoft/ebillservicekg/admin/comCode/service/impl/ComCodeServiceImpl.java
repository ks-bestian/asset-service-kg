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
        return comCodeMapper.getGrpCodeList(param);
    }

    @Override
    public List<ComCodeVo> getCodeList(HashMap<String, Object> param) {
        return comCodeMapper.getComCodeList(param);
    }

    @Override
    public ComCodeVo getGrpCodeById(String grpCode) {
        return comCodeMapper.getGrpCodeById(grpCode);
    }

    @Override
    public ComCodeVo getComCodeById(String codeId) {
        return comCodeMapper.getComCodeById(codeId);
    }

    @Override
    public ComCodeVo createGrpCode(ComCodeVo comCodeVo) {
        comCodeVo.setGrpCode(comCodeMapper.createGrpId());
        comCodeMapper.insertGrpCode(comCodeVo);
        return comCodeVo;
    }

    @Override
    public ComCodeDetailVo createComCode(ComCodeDetailVo comCodeDetailVo) {
        boolean existId = comCodeMapper.existCodeId(comCodeDetailVo.getCodeId());
        if (existId) {
            throw new DuplicateKeyException("중복코드가 존재합니다. : " + comCodeDetailVo.getCodeId());
        } else {
            comCodeMapper.insertComCode(comCodeDetailVo);
        }
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
    public void deleteGrpCode(Integer grpCode) {
        boolean existCodeId = comCodeMapper.existCodeIdInGrp(grpCode);
        if (!existCodeId) {
            comCodeMapper.deleteGrpCode(grpCode);
        } else {
            throw new UnsupportedOperationException("하위코드가 존재합니다. : " + grpCode);
        }
    }

    @Override
    public void deleteComCode(String codeId) {
        comCodeMapper.deleteComCode(codeId);
    }
}
