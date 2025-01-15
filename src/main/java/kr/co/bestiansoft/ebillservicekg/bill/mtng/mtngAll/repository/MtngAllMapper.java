package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;

@Mapper
public interface MtngAllMapper {
    List<MtngAllVo> getMtngList (HashMap<String, Object> param);
    MtngAllVo getMtngById(String mtngId);
}
