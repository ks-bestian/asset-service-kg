package kr.co.bestiansoft.ebillservicekg.asset.equip.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipResponse;

public interface EquipService {
    int createEquip(EquipRequest equipRequest);
    List<EquipResponse> getEquipList(HashMap<String, Object> parmas);
    List<EquipDetailVo> getEquipItemLists(HashMap<String, Object> params);
    EquipResponse getEquipDetail(String eqpmntId);
    int updateEquip(EquipRequest equipRequest);
    void deleteEquip(List<String> ids);
}
