package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.impl;


import kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository.AmsImgMapper;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmsImgServiceImpl implements AmsImgService {
    private final AmsImgMapper amsImgMapper;



    @Override
    public List<AmsImgVo> getImgListByEqpmntId(String eqpmntId) {
        return amsImgMapper.getImgListByEqpmntId(eqpmntId);
    }

    @Override
    public void deleteImg(String eqpmntId) {
        amsImgMapper.deleteImg(eqpmntId);
    }
}
