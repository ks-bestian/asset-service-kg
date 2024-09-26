package kr.co.bestiansoft.ebillservicekg.admin.log.repository;

import kr.co.bestiansoft.ebillservicekg.admin.log.repository.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
