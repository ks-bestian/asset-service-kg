package kr.co.bestiansoft.ebillservicekg.admin.log.repository;

import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogRead;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.Logs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {

    List<Logs> selectLogs(LogRead read);
    Long selectLogCount(LogRead read);
}
