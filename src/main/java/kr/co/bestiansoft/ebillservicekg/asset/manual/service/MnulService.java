package kr.co.bestiansoft.ebillservicekg.asset.manual.service;

import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MnulService {
    int createMnul(List<MnulVo> mnulVo, String eqpmntId, String mnlSe);
    
    int createMnulFromUrl(List<MnulVo> mnulVoList, String eqpmntId);
    int upsertMnulFromUrl(List<MnulVo> mnulVoList, String eqpmntId);

    List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds);

    List<MnulVo> getMnulListByEqpmntId(String eqpmntId, String videoYn);
    MnulVo getMnlByMnlId(String mnlId);

    void deleteMnul(String eqpmntId);
    void deleteMnulById(List<String> ids);
    int upsertMnul(List<MnulVo> mnulVoList, String eqpmntId, String mnlSe);
    Resource loadVideoAsResource(String videoId)throws IOException;
    Resource videoMnlAsResource(String mnlId)throws IOException;
    Resource downloadFile(String fileId);

}
