package kr.co.bestiansoft.ebillservicekg.admin.lawInfo.repository;

import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.vo.LawInfoVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface LawInfoMapper {
    List<LawInfoVo> getLawInfoList(HashMap<String, Object> param);
    LawInfoVo getLawInfoDetail(Long lawId);
    int insertLawInfo(LawInfoVo lawInfoVo);
    int updateLawInfo(LawInfoVo lawInfoVo);
    void deleteLawInfo(Long lawId);
}
