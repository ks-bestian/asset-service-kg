package kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmsImgMapper {
    List<AmsImgVo> getImgListByEqpmntId(List<String> eqpmntIds);
    List<AmsImgVo> getImgListByInstlId(List<String> instlIds);
    AmsImgVo getImgByFileNm(String fileNm);
    void deleteImg(String eqpmntId);
    void deleteThumbnailImg(String eqpmntId);
    void deleteDetailImg(String eqpmntId);
    int saveImg(AmsImgVo amsImgVo);
    List<AmsImgVo> getDetailListByEqpmntId(String eqpmntId);
    AmsImgVo getThumbnailByEqpmntId(String eqpmntId);
    List<AmsImgVo> getInstallListByEqpmntId(String eqpmntId);
    AmsImgVo getImgByInstlId(String instlId);

    AmsImgVo getImgVoByImgId(String imgId);
    
    void deleteNotInfileNm(@Param("eqpmntId") String eqpmntId, @Param("fileNmList") List<String> fileNmList);

}
