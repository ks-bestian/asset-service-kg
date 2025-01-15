package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository.MtngAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.MtngAllService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngAllServiceImpl implements MtngAllService {
    private final MtngAllMapper mtngAllMapper;

    @Override
    public List<MtngAllVo> getMtngList(HashMap<String, Object> param) {
        List<MtngAllVo> result = mtngAllMapper.getMtngList(param);
        return result;
    }

    @Override
    public MtngAllVo getMtngById(String mtngId) {
    	MtngAllVo dto = mtngAllMapper.getMtngById(mtngId);
        return dto;
    }
}