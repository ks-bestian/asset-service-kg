package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmsImgService {
    List<AmsImgVo> getImgListByEqpmntId(String eqpmntId);
    void deleteImg(String eqpmntId);
    int saveImgs(MultipartFile[] files, String eqpmntId, String instlId, String imgSe);

}
