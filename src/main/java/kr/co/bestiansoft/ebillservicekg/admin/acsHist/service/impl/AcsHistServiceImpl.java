package kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.impl;


import kr.co.bestiansoft.ebillservicekg.admin.acsHist.repository.AcsHistMapper;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AcsHistServiceImpl implements AcsHistService {
    private final AcsHistMapper acsHistMapper;

    @Override
    public List<AcsHistVo> getAcsHistList(HashMap<String, Object> param) {
        return acsHistMapper.getAcsHistList(param);
    }

    @Override
    public void createAcsHist(AcsHistVo acsHistVo) {
        acsHistMapper.insertAcsHist(acsHistVo);
    }
}
