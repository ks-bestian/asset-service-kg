package kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AmsImgMapper {
    List<AmsImgVo> getImgListByEqpmntId(List<String> eqpmntIds);
    void deleteImg(String eqpmntId);
    int saveImg(AmsImgVo amsImgVo);
    List<AmsImgVo> getDetailListByEqpmntId(String eqpmntId);
    AmsImgVo getImgByInstlId(String instlId);


}
