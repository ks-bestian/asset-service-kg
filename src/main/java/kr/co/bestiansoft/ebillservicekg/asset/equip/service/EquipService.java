package kr.co.bestiansoft.ebillservicekg.asset.equip.service;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipResponse;

import java.util.HashMap;
import java.util.List;

public interface EquipService {
    int createEquip(EquipRequest equipRequest, String mnlVoJson, String installVoJson);
    List<EquipResponse> getEquipList(HashMap<String, Object> parmas);
    List<EquipDetailVo> getEquipItemLists(HashMap<String, Object> params);
    EquipResponse getEquipDetail(String eqpmntId);
    int updateEquip(EquipRequest equipRequest);
    void deleteEquip(List<String> ids);
}
