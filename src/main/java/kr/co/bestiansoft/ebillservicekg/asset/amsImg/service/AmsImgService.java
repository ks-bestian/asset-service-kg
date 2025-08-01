package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface AmsImgService {
    List<AmsImgVo> getImgListByEqpmntId(List<String> eqpmntId);
    List<AmsImgVo> getImgListByInstlId(List<String> instlId);
    void deleteImg(String eqpmntId);
    int saveImgs(MultipartFile[] files, String eqpmntId, String instlId, String imgSe);
    List<AmsImgVo> getDetailListByEqpmntId(String eqpmntId);
    AmsImgVo getImgByInstlId(String instlId);
    AmsImgVo getImgVoByImgId(String imgId);

}
