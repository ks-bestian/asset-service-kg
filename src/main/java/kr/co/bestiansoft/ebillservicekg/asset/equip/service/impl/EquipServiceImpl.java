package kr.co.bestiansoft.ebillservicekg.asset.equip.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.repository.EquipMapper;
import kr.co.bestiansoft.ebillservicekg.asset.equip.service.EquipService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipDetailVo;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipResponse;
import kr.co.bestiansoft.ebillservicekg.asset.faq.service.FaqService;
import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EquipServiceImpl implements EquipService {

    private final ObjectMapper objectMapper;
    private final EquipMapper equipMapper;
    private final MnulService mnulService;
    private final InstallService installService;
    private final FaqService faqService;
    private final AmsImgService amsImgService;
    @Value("${file.upload.path}")
    private String fileUploadDir;

    @Transactional
    @Override
    public int createEquip(EquipRequest equipRequest, String mnlVoJson, String installVoJson, String faqVoJson,  Map<String, MultipartFile> fileMap) {

        //1.장비정보
        String eqpmntId = StringUtil.getEqpmntUUUID();
        equipRequest.setEqpmntId(eqpmntId);
        equipRequest.setRgtrId(new SecurityInfoUtil().getAccountId());

        equipMapper.createEquip(equipRequest);

        //1. 장비 메뉴얼 파일, 이미지 저장
        if (equipRequest.getFiles() != null) {
            List files = new ArrayList<>();
            for (MultipartFile file : equipRequest.getFiles()) {
                MnulVo vo = MnulVo.builder()
                        .file(file)
                        .build();
                files.add(vo);
            }
            mnulService.createMnul(files, eqpmntId, "file");
        }

        if (equipRequest.getDtlImg() != null) {
            amsImgService.saveImgs(equipRequest.getDtlImg(), eqpmntId, null, "detail");
        }

        if (equipRequest.getThumbnail() != null) {
            amsImgService.saveImgs(equipRequest.getThumbnail(), eqpmntId, null, "thumbnail");
        }



        List<MnulVo> mnulList = null;
        List<InstallVo> installVoList = null;
        List<FaqVo> faqList = null;

        try {
            if (mnlVoJson != null) {
                mnulList = objectMapper.readValue(mnlVoJson, new TypeReference<List<MnulVo>>() {});
            }
            if (installVoJson != null) {
                installVoList = objectMapper.readValue(installVoJson, new TypeReference<List<InstallVo>>() {});
            }
            if(faqVoJson != null) {
                faqList = objectMapper.readValue(faqVoJson, new TypeReference<List<FaqVo>>() {});
            }


        } catch (IOException e) {
            System.err.println("ERROR : " + e);
        }

        for (MnulVo vo : mnulList) {
            if(vo.getFileId() != null) {
                vo.setFile(fileMap.get(vo.getFileId()));
            }
        }

        for (InstallVo vo : installVoList) {
            if(vo.getFileId() != null) {
                vo.setFile(fileMap.get(vo.getFileId()));
            }
        }

        //2.영상메뉴얼
        mnulService.createMnulFromUrl(mnulList, eqpmntId);
        //3.설치정보
        installService.createInstall(installVoList, eqpmntId);

        //4. faq
        faqService.createFaq(faqList, eqpmntId);

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
    public List<EquipDetailVo> getEquipListAll(HashMap<String, Object> params) {
        //장비 정보 + 업체명 + 제품구분
        List<EquipDetailVo> equipList = equipMapper.getEquipList(params);

        return equipList;
    }

    @Override
    public EquipResponse getEquipDetail(String eqpmntId) {
        EquipResponse result = new EquipResponse();
        result.setEquipDetailVo(equipMapper.getDetailEquip(eqpmntId));

        //todo 이미지 : thumnail & 설치 이미지
        List<String> id = Arrays.asList(eqpmntId);

        result.setAmsImgList(amsImgService.getDetailListByEqpmntId(eqpmntId));

        result.setInstallList(installService.getInstallList(eqpmntId));

        result.setMnulList(mnulService.getMnulListByEqpmntId(eqpmntId, "file"));
        result.setFaqList(faqService.getFaqList(eqpmntId));
        return result;
    }

    @Transactional
    @Override
    public int updateEquip(EquipRequest equipRequest,  String mnlVoJson, String installVoJson, String faqVoJson,  Map<String, MultipartFile> fileMap) {
        String eqpmntId = equipRequest.getEqpmntId();
        List<MnulVo> mnulList = null;
        List<InstallVo> installVoList = null;
        List<FaqVo> faqList = null;


        try {
            if (mnlVoJson != null) {
                mnulList = objectMapper.readValue(mnlVoJson, new TypeReference<List<MnulVo>>() {});
            }
            if (installVoJson != null) {
                installVoList = objectMapper.readValue(installVoJson, new TypeReference<List<InstallVo>>() {});
            }
            if(faqVoJson != null) {
                faqList = objectMapper.readValue(faqVoJson, new TypeReference<List<FaqVo>>() {});
            }


        } catch (IOException e) {
            System.err.println("ERROR : " + e);
        }
        
        
        //1.장비정보
        if (equipRequest.getFiles() != null) {
            List files = new ArrayList<>();
            for (MultipartFile file : equipRequest.getFiles()) {
                MnulVo vo = MnulVo.builder()
                        .file(file)
                        .build();
                files.add(vo);
            }
            mnulService.upsertMnul(files, eqpmntId);
        }

        equipRequest.setMdfrId(new SecurityInfoUtil().getAccountId());
        equipMapper.updateEquip(equipRequest);

        //장비 썸네일, 상세 이미지
        amsImgService.deleteImg(eqpmntId);
        if (equipRequest.getDtlImg() != null) {
            amsImgService.saveImgs(equipRequest.getDtlImg(), eqpmntId, null, "detail");
        }

        if (equipRequest.getThumbnail() != null) {
            amsImgService.saveImgs(equipRequest.getThumbnail(), eqpmntId, null, "thumbnail");
        }

        //2.영상메뉴얼
        mnulService.upsertMnul(mnulList, eqpmntId);

        //3.설치정보(위치 : 공통코드)
        installService.upsertInstl(installVoList, eqpmntId);
        
        //4. faq
        faqService.updateFaq(faqList, eqpmntId);

        return 1;
    }

    @Transactional
    @Override
    public void deleteEquip(List<String> ids) { //eqpmnt_id 관련 모든 데이터 삭제
        for (String equipId : ids) {
        	faqService.deleteFaq(equipId);
            installService.deleteInstall(equipId);
            mnulService.deleteMnul(equipId);
            amsImgService.deleteImg(equipId);
            equipMapper.deleteEquip(equipId);
            

        }
    }

    @Override
    public Resource loadThumbnail(String eqpmntId) throws IOException {
        List<String> id = Arrays.asList(eqpmntId);
        //장비 상세 이미지
        List<AmsImgVo> imgList = amsImgService.getImgListByEqpmntId(id);
        Optional<AmsImgVo> vo = imgList.stream().filter(item -> "thumbnail".equals(item.getImgSe())).findFirst();
        AmsImgVo imgVo = vo.orElseThrow(() -> new FileNotFoundException("No Img"));
        return FileUtil.loadFile(makeLoadPath(imgVo));
    }
    
    @Override
    public Resource loadImg(String imgId) throws IOException {
        //장비 상세 이미지
        AmsImgVo vo = amsImgService.getImgVoByImgId(imgId);

        return FileUtil.loadFile(makeLoadPath(vo));
    }
    
    @Override
    public Resource loadInstallImg(String instlId) throws IOException {
        List<String> id = Arrays.asList(instlId);
        //장비 상세 이미지
        List<AmsImgVo> imgList = amsImgService.getImgListByInstlId(id);
        Optional<AmsImgVo> vo = imgList.stream().filter(item -> "installImg".equals(item.getImgSe())).findFirst();
        AmsImgVo imgVo = vo.orElseThrow(() -> new FileNotFoundException("No Img"));
        return FileUtil.loadFile(makeLoadPath(imgVo));
    }
    
    public String makeLoadPath(AmsImgVo vo) {
        String fileNameWithExt = vo.getFileExtn() != null && !vo.getFileExtn().isBlank()
                ? vo.getFileNm() + "." + vo.getFileExtn()
                : vo.getFileNm();
        String loadPath = Paths.get(fileUploadDir, vo.getFilePath(), fileNameWithExt).toString();
        return loadPath;
    }

    
}
