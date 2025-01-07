package kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.repository.BaseCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class BaseCodeServiceImpl implements BaseCodeService {

    private final BaseCodeMapper baseCodeMapper;

    @Override
    public List<BaseCodeVo> getBaseCodeList(HashMap<String, Object> param) {
        return baseCodeMapper.selectListBaseCode(param);
    }


    @Override
    public BaseCodeVo createBaseCode(BaseCodeVo baseCodeVo) {
    	baseCodeMapper.insertBaseCode(baseCodeVo);
        return baseCodeVo;
    }

    @Override
    public int updateBaseCode(BaseCodeVo baseCodeVo) {
        return baseCodeMapper.updateBaseCode(baseCodeVo);
    }

    @Override
    public void deleteBaseCode(List<Long> ids) {
        for (Long id : ids) {
            baseCodeMapper.deleteBaseCode(id);
        }
    }

    @Override
    public BaseCodeVo getBaseCodeById(Long baseCode) {
        return baseCodeMapper.selectBaseCode(baseCode);
    }
}
