package kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.repository.BzentyMapper;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.BzentyService;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
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
public class BzentyServiceImpl implements BzentyService {

    private final BzentyMapper bzentyMapper;

    @Override
    public List<BzentyVo> getBzentyList(HashMap<String, Object> param) {
        return bzentyMapper.selectListBzenty(param);
    }

    @Override
    public BzentyVo getBzentyDetail(String bzentyId) {
        return bzentyMapper.selectBzenty(bzentyId);
    }

    @Override
    public BzentyVo createBzenty(BzentyVo bzentyVo) {
        String rgtrId = new SecurityInfoUtil().getAccountId();
    	String bzentyId = StringUtil.getBzentyUUID();

        bzentyVo.setRgtrId(rgtrId);
        bzentyVo.setBzentyId(bzentyId);

        bzentyMapper.insertBzenty(bzentyVo);

        return bzentyVo;
    }

    @Override
    public int updateBzenty(BzentyVo bzentyVo) {
        String modId = new SecurityInfoUtil().getAccountId();
        bzentyVo.setMdfrId(modId);
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
