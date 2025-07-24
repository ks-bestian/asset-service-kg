package kr.co.bestiansoft.ebillservicekg.asset.equip.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.repository.EquipMapper;
import kr.co.bestiansoft.ebillservicekg.asset.equip.service.EquipService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.*;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EquipServiceImpl implements EquipService {

    private final EquipMapper equipMapper;
    private final MnulService mnulService;
    private final InstallService installService;
    private final AmsImgService amsImgService;

    @Transactional
    @Override
    public int createEquip(EquipRequest equipRequest) {

        //1.제품정보(pdf메뉴얼, 이미지저장 보류)
        String eqpmntId = StringUtil.getEqpmntUUUID();

        equipRequest.setEqpmntId(eqpmntId);
//        equipRequest.setRgtrId(new SecurityInfoUtil().getAccountId());

        equipMapper.createEquip(equipRequest);

        mnulService.createMnul(equipRequest.getMnulVoList(), eqpmntId);
        //2.영상메뉴얼 보류

        //3.설치정보
        installService.createInstall(equipRequest.getInstallVoList(), eqpmntId);

        return 1;
    }

    @Override
    public List<EquipResponse> getEquipList(HashMap<String, Object> params) { //todo 수정
        //장비 정보 + 업체명 + 제품구분
        List<EquipDetailVo> equipList = equipMapper.getEquipList(params);

        List<String> eqpmntIds = equipList.stream()
                .map(EquipDetailVo::getEqpmntId)
                .collect(Collectors.toList());

        //메뉴얼 정보
        List<MnulVo> mList = mnulService.getMnulListByEquipIds(eqpmntIds);

        Map<String, List<MnulVo>> mnulMap = mList.stream()
                .collect(Collectors.groupingBy(MnulVo::getEqpmntId));

        //이미지
//        List<AmsImgVo> imgs = amsImgService.getImgListByEqpmntId(eqpmntIds);

        //result
        List<EquipResponse> result = new ArrayList<>();
        for (EquipDetailVo raw : equipList) {
            EquipResponse eq = new EquipResponse();

            eq.setEquipDetailVo(new EquipDetailVo(raw));
            eq.setMnulList(mnulMap.getOrDefault(raw.getEqpmntId(), new ArrayList<>()));

            result.add(eq);
        }

        return result;
    }

    @Override
    public List<EquipDetailVo> getEquipItemLists(HashMap<String, Object> params) {

        //장비정보(equip), 업체명, 제품구분, 메뉴얼정보
        List<EquipDetailVo> equipList = equipMapper.getEquipList(params);
        List<String> equipIds = equipList.stream()
                .map(EquipDetailVo::getEqpmntId)
                .collect(Collectors.toList());

        //todo 영상 메뉴얼 result 추가하기
        List<MnulVo> mnulList = mnulService.getMnulListByEquipIds(equipIds);

        return equipList;
    }

    @Override
    public EquipResponse getEquipDetail(String eqpmntId) {
        EquipResponse result = new EquipResponse();
        result.setEquipDetailVo(equipMapper.getDetailEquip(eqpmntId));

        result.setAmsImgList(amsImgService.getImgListByEqpmntId(eqpmntId));

        result.setInstallList(installService.getInstallList(eqpmntId));

        result.setMnulList(mnulService.getMnulListByEqpmntId(eqpmntId));
        return result;
    }

    @Transactional
    @Override
    public int updateEquip(EquipRequest equipRequest) {
        String eqpmntId = equipRequest.getEqpmntId();
        //1.제품정보(pdf메뉴얼, 이미지저장 보류)
//        equipRequest.setMdfrId(new SecurityInfoUtil().getAccountId());
        equipMapper.updateEquip(equipRequest);

        installService.deleteInstall(eqpmntId);
        mnulService.deleteMnul(eqpmntId);
        amsImgService.deleteImg(eqpmntId);

        mnulService.createMnul(equipRequest.getMnulVoList(), eqpmntId);

        //2.영상메뉴얼 보류

        //3.설치정보(위치 : 공통코드)
        installService.createInstall(equipRequest.getInstallVoList(), eqpmntId);

        return equipMapper.updateEquip(equipRequest);
    }

    @Transactional
    @Override
    public void deleteEquip(List<String> ids) { //eqpmnt_id 관련 모든 데이터 삭제
        for(String equipId : ids) {
            installService.deleteInstall(equipId);
            mnulService.deleteMnul(equipId);
            amsImgService.deleteImg(equipId);
            equipMapper.deleteEquip(equipId);

        }
    }
}
