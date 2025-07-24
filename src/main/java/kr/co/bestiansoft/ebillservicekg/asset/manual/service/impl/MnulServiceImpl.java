package kr.co.bestiansoft.ebillservicekg.asset.manual.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.asset.manual.repository.MnulMapper;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MnulServiceImpl implements MnulService {
    private final MnulMapper mnulMapper;

    @Override
    public int createMnul(List<MnulVo> mnulVoList, String eqpmntId) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
//            mnulVo.setMnlSe("pdf");
            mnulVo.setMnlId(StringUtil.getMnlUUID());
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setSeq(i);
            mnulVo.setMnlLng("uz"); //todo 수정
//            mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());
            i++;
        }


        return mnulMapper.createMnul(mnulVoList);
    }

    @Override
    public List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds) {
        return mnulMapper.getMnulListByEquipIds(eqpmntIds);
    }

    @Override
    public List<MnulVo> getMnulListByEqpmntId(String eqpmntId) {
        return mnulMapper.getMnulListByEqpmntId(eqpmntId);
    }

    @Override
    public void deleteMnul(String eqpmntId) {
        mnulMapper.deleteMnul(eqpmntId);
    }

    @Override
    public void deleteMnulById(List<String> ids) {
        for(String id : ids) {
            mnulMapper.deleteMnulById(id);
        }
    }
}
