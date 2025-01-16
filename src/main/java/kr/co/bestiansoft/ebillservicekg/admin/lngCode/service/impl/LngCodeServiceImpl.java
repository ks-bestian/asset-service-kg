package kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.impl;


import kr.co.bestiansoft.ebillservicekg.admin.lngCode.repository.LngCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.LngCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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
        return lngCodeMapper.selectListLngCode(param);
    }

    @Override
    public LngCodeVo createLngCode(LngCodeVo lngCodeVo) {
        lngCodeVo.setRegId(new SecurityInfoUtil().getAccountId());
        lngCodeMapper.insertLngCode(lngCodeVo);

        return lngCodeVo;
    }

    @Override
    public int updateLngCode(LngCodeVo lngCodeVo) {
        lngCodeVo.setModId(new SecurityInfoUtil().getAccountId());
        return lngCodeMapper.updateLngCode(lngCodeVo);
    }

    @Override
    public void deleteLngCode(List<Long> lngId) {
        for (Long id : lngId) {
            lngCodeMapper.deleteLngCode(id);
        }
    }

    @Override
    public LngCodeVo getLngCodeById(Long lngId) {
        return lngCodeMapper.selectLngCode(lngId);
    }
}
