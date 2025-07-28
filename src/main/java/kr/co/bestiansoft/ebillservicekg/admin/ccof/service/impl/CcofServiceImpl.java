package kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CcofServiceImpl implements CcofService {

    private final CcofMapper ccofMapper;

    /**
     * Retrieves a list of CCOF (Corporate Compliance Office Function) information based on the provided parameters.
     *
     * @param param a HashMap containing parameters to filter the CCOF list. The keys and values in the map
     *              should correspond to the desired query criteria.
     * @return a list of CcofVo objects matching the given parameters.
     */
    @Override
    public List<CcofVo> getCcofList(HashMap<String, Object> param) {
        return ccofMapper.selectListCcof(param);
    }

}
