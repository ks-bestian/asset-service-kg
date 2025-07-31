package kr.co.bestiansoft.ebillservicekg.asset.equip.service;

import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipResponse;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipVo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EquipService {
    int createEquip(EquipRequest equipRequest, String mnlVoJson, String installVoJson, String faqVoJson,  Map<String, MultipartFile> fileMap);
    //왜 이렇게 하죠????
    List<EquipResponse> getEquipList(HashMap<String, Object> parmas);
    List<EquipDetailVo> getEquipListAll(HashMap<String, Object> parmas);
    EquipResponse getEquipDetail(String eqpmntId);
    int updateEquip(EquipRequest equipRequest, String faqVoJson);
    void deleteEquip(List<String> ids);
    Resource loadThumbnail(String eqpmntId) throws IOException;
    Resource loadImg(String imgId) throws IOException;
    Resource loadInstallImg(String instlId) throws IOException;
}
