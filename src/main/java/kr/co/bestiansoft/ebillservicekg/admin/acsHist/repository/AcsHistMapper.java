package kr.co.bestiansoft.ebillservicekg.admin.acsHist.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;

@Mapper
public interface AcsHistMapper {
    List<AcsHistVo> getAcsHistList(HashMap<String, Object> param);
    List<AcsHistVo> getBillHistList(HashMap<String, Object> param);
    void createAcsHist(AcsHistVo acsHistVo);
    void createBillHist(AcsHistVo acsHistVo);
    void deleteOldAcsHist();
    void deleteOldBillHist();
}
