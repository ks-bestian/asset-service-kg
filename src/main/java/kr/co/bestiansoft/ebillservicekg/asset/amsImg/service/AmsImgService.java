package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;

public interface AmsImgService {
    List<AmsImgVo> getImgListByEqpmntId(String eqpmntId);
    void deleteImg(String eqpmntId);

}
