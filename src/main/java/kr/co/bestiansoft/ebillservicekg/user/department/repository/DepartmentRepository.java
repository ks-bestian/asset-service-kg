package kr.co.bestiansoft.ebillservicekg.user.department.repository;

import kr.co.bestiansoft.ebillservicekg.user.department.repository.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {
}
