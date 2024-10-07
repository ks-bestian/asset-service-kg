package kr.co.bestiansoft.ebillservicekg.admin.menu.repository;

import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
