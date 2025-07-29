package kr.co.bestiansoft.ebillservicekg.asset.equip.repository;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface EquipMapper {

    int createEquip(EquipRequest equipRequest);
    List<EquipDetailVo> getEquipList(HashMap<String, Object> params);
    EquipDetailVo getDetailEquip(String eqpmntId);
    int updateEquip(EquipRequest equipRequest);
    void deleteEquip(String eqpmntId);
}
