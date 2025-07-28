package kr.co.bestiansoft.ebillservicekg.asset.equip.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;

@Mapper
public interface EquipMapper {

    int createEquip(EquipRequest equipRequest);
    List<EquipDetailVo> getEquipList(HashMap<String, Object> params);
    EquipDetailVo getDetailEquip(String eqpmntId);
    int updateEquip(EquipRequest equipRequest);
    void deleteEquip(String eqpmntId);
}
