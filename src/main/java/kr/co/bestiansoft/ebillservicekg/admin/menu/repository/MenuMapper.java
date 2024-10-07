package kr.co.bestiansoft.ebillservicekg.admin.menu.repository;

import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuEntity> selectMenuByUserId(String userId);
}
