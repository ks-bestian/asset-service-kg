package kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class BaseCodeServiceImpl implements BaseCodeService {

    @Override
    public List<BaseCodeVo> getBaseCodeList(HashMap<String, Object> param) {
        return null;
    }

    @Override
    public BoardVo createBaseCode(BaseCodeVo baseCodeVo) {
        return null;
    }

    @Override
    public int updateBaseCode(BaseCodeVo baseCodeVo) {
        return 0;
    }

    @Override
    public void deleteBaseCode(List<Long> ids) {

    }

    @Override
    public BoardVo getBaseCodeById(Long baseCode) {
        return null;
    }
}
