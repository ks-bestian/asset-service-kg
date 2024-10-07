package kr.co.bestiansoft.ebillservicekg.admin.menu.repository.entity;

import kr.co.bestiansoft.ebillservicekg.config.jpa.BaseEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Table(name = "com_menu")
@Entity
public class MenuEntity extends BaseEntity {

    @Id
    Long menuId;

    String menuNm1;
    String menuNm2;
    Long uprMenuId;
    String path ;
    Integer ord ;
    String rmk;
    String useYn;

}
