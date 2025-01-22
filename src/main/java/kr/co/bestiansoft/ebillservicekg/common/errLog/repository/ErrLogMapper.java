package kr.co.bestiansoft.ebillservicekg.common.errLog.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.common.errLog.vo.ErrLogVo;

@Mapper
public interface ErrLogMapper {
	void insertErrLog(ErrLogVo errLogVo);
}
