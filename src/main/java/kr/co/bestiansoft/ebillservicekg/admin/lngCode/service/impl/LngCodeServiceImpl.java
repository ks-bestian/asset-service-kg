package kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.impl;


import kr.co.bestiansoft.ebillservicekg.admin.lngCode.repository.LngCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.LngCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class LngCodeServiceImpl implements LngCodeService {
    private final LngCodeMapper lngCodeMapper;

    @Override
    public List<LngCodeVo> getLngCodeList(HashMap<String, Object> param) {
        return lngCodeMapper.getLngCodeList(param);
    }

    @Override
    public LngCodeVo createLngCode(LngCodeVo lngCodeVo) {
        lngCodeMapper.insertLngCode(lngCodeVo);

        return lngCodeVo;
    }

    @Override
    public int updateLngCode(LngCodeVo lngCodeVo) {
        return lngCodeMapper.updateLngCode(lngCodeVo);
    }

    @Override
    public void deleteLngCode(Long lngId) {
        lngCodeMapper.deleteLngCode(lngId);
    }

    @Override
    public LngCodeVo getLngCodeById(Long lngId) {
        return lngCodeMapper.getLngCodeById(lngId);
    }
}
