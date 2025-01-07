package kr.co.bestiansoft.ebillservicekg.admin.acsHist.repository;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AcsHistMapper {
    List<AcsHistVo> getAcsHistList(HashMap<String, Object> param);
    void createAcsHist(AcsHistVo acsHistVo);
}
