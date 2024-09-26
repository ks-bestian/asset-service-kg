package kr.co.bestiansoft.ebillservicekg.user.employee.repository;

import kr.co.bestiansoft.ebillservicekg.user.employee.repository.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
}
