package kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;

@Mapper
public interface AmsImgMapper {
    List<AmsImgVo> getImgListByEqpmntId(String eqpmntId);
    void deleteImg(String eqpmntId);
}
