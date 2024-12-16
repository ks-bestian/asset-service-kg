package kr.co.bestiansoft.ebillservicekg.admin.lawInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.repository.LawInfoMapper;
import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.service.LawInfoService;
import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.vo.LawInfoVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
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
public class LawInfoServiceImpl implements LawInfoService {

    private final LawInfoMapper lawInfoMapper;
    @Override
    public List<LawInfoVo> getLawInfoList(HashMap<String, Object> param) {
        return lawInfoMapper.getLawInfoList(param);
    }

    @Override
    public LawInfoVo getLawInfoDetail(Long lawId) {
        return lawInfoMapper.getLawInfoDetail(lawId);
    }

    @Override
    public LawInfoVo createLawInfo(LawInfoVo lawInfoVo) {
        lawInfoMapper.insertLawInfo(lawInfoVo);
        return lawInfoVo;
    }

    @Override
    public int updateLawInfo(LawInfoVo lawInfoVo) {
        return lawInfoMapper.updateLawInfo(lawInfoVo);
    }

    @Override
    public void deleteLawInfo(List<Long> ids) {
        for(Long lawId : ids) {
            lawInfoMapper.deleteLawInfo(lawId);
        }

    }
}
