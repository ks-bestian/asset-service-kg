package kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CcofServiceImpl implements CcofService {

    private final CcofMapper ccofMapper;
    @Override
    public List<CcofVo> getCcofList(HashMap<String, Object> param) {
        return ccofMapper.selectListCcof(param);
    }

}
