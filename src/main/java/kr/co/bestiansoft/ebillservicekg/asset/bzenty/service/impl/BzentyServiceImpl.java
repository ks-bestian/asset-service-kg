package kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.impl;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.repository.BzentyMapper;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.BzentyService;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BzentyServiceImpl implements BzentyService {
	
    private final BzentyMapper bzentyMapper;

    @Override
    public List<BzentyVo> getBzentyList(HashMap<String, Object> param) {
        return bzentyMapper.selectListBzenty(param);
    }

    @Override
    public BzentyVo getBzentyDetail(Long seq) {
        return bzentyMapper.selectBzenty(seq);
    }

    @Override
    public BzentyVo createBzenty(BzentyVo bzentyVo) {
        //String regId = new SecurityInfoUtil().getAccountId();
    	// TODO :: 로그인 처리해야함
    	String regId = "admin";
    	
        bzentyVo.setRegId(regId);

        bzentyMapper.insertBzenty(bzentyVo);

        return bzentyVo;
    }

    @Override
    public int updateBzenty(BzentyVo bzentyVo) {
//        String modId = new SecurityInfoUtil().getAccountId();
//        bzentyVo.setModId(modId);
        bzentyMapper.updateBzenty(bzentyVo);

        return 0;
    }

    @Override
    public void deleteBzenty(List<String> seq) {
        for (String id : seq) {
            bzentyMapper.deleteBzenty(id);
        }
    }

}
