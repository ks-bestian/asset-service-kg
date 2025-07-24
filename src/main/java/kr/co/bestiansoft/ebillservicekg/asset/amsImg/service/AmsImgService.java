package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;

import java.util.List;

public interface AmsImgService {
    List<AmsImgVo> getImgListByEqpmntId(String eqpmntId);
    void deleteImg(String eqpmntId);

}
