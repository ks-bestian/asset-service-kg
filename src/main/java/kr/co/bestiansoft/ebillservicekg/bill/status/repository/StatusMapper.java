package kr.co.bestiansoft.ebillservicekg.bill.status.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.status.vo.StatusVo;

@Mapper
public interface StatusMapper {

	List<StatusVo> getMtngList(HashMap<String, Object> param);

	List<StatusVo> getMonitorList(HashMap<String, Object> param);

}
