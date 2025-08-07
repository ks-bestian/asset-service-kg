package kr.co.bestiansoft.ebillservicekg.asset.equip.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository.AmsImgMapper;
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
import kr.co.bestiansoft.ebillservicekg.asset.manual.repository.MnulMapper;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulDto;
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
    private final MnulMapper mnulMapper;
    private final InstallService installService;
    private final FaqService faqService;
    private final AmsImgService amsImgService;
    private final AmsImgMapper amsImgMapper;
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
        
        EquipDetailVo equipDetailVo = new EquipDetailVo();
        
        List<MnulVo> mnulVoList = new ArrayList<MnulVo>();
        
        List<InstallVo> installVoList = new ArrayList<InstallVo>();
        
        List<FaqVo> faqVoList = new ArrayList<FaqVo>();
        
        equipDetailVo = equipMapper.getDetailEquip(eqpmntId);
        mnulVoList = mnulService.getMnulListByEqpmntId(eqpmntId, "video");
        installVoList = installService.getInstallList(eqpmntId);
        faqVoList = faqService.getFaqList(eqpmntId);
        

        equipDetailVo.setFiles(mnulService.getMnulListByEqpmntId(eqpmntId, "file"));
        
        equipDetailVo.setDtlImg(amsImgService.getDetailListByEqpmntId(eqpmntId));      
        equipDetailVo.setThumbnail(amsImgMapper.getThumbnailByEqpmntId(eqpmntId));
        
        for (MnulVo vo : mnulVoList) {
        	MnulDto dto = MnulDto.from(vo);
            vo.setVideoFile(dto); // 자기 자신을 설정
        }
        for (InstallVo vo : installVoList) {
        	AmsImgVo amsImgVo = amsImgMapper.getImgByInstlId(vo.getInstlId());
            vo.setInstlFile(amsImgVo); // 자기 자신을 설정
        }

        
        
        result.setEquipDetailVo(equipDetailVo);
        result.setMnulList(mnulVoList);
        result.setInstallList(installVoList);
        result.setFaqList(faqVoList);
        
        result.setAmsImgList(amsImgService.getDetailListByEqpmntId(eqpmntId));
        
        return result;
    }

    @Transactional
    @Override
    public int updateEquip(EquipRequest equipRequest, Map<String, MultipartFile> fileMap) throws JsonMappingException, JsonProcessingException {
        String eqpmntId = equipRequest.getEqpmntId();
        List<MnulVo> mnulList = null;
        List<InstallVo> installVoList = null;
        List<FaqVo> faqList = null;
        List<Map<String, Object>> thumbnailKeepIds = objectMapper.readValue(equipRequest.getThumbnailKeep(), new TypeReference<>() {});
        List<Map<String, Object>> filesKeepIds = objectMapper.readValue(equipRequest.getFilesKeep(), new TypeReference<>() {});
        List<Map<String, Object>> dtlImglKeepIds = objectMapper.readValue(equipRequest.getDtlImgKeep(), new TypeReference<>() {});
        List<Map<String, Object>> videoFileKeepIds = objectMapper.readValue(equipRequest.getVideoFileKeep(), new TypeReference<>() {});
        List<Map<String, Object>> instlFileKeepIds = objectMapper.readValue(equipRequest.getInstlFileKeep(), new TypeReference<>() {});
        
        
        List<String> thumbnailKeepList = thumbnailKeepIds.stream()
                .map(file -> (String) file.get("fileNm"))
                .collect(Collectors.toList());
        List<String> filesKeepList = filesKeepIds.stream()
        		.map(file -> (String) file.get("fileNm"))
        		.collect(Collectors.toList());
        List<String> dtlImglKeepList = dtlImglKeepIds.stream()
        		.map(file -> (String) file.get("fileNm"))
        		.collect(Collectors.toList());
        List<String> videoFileKeepList = videoFileKeepIds.stream()
        		.map(file -> (String) file.get("fileNm"))
        		.collect(Collectors.toList());
        List<String> instlFileKeepList = instlFileKeepIds.stream()
        		.map(file -> (String) file.get("fileNm"))
        		.collect(Collectors.toList());
        
        
        List<String> mergedMnulList = new ArrayList<>();
        if (filesKeepList != null) {
        	mergedMnulList.addAll(filesKeepList);
        }
        if (videoFileKeepList != null) {
        	mergedMnulList.addAll(videoFileKeepList);
        }
        
        List<String> mergedImgList = new ArrayList<>();
        if (dtlImglKeepList != null) {
        	mergedImgList.addAll(dtlImglKeepList);
        }
        if (thumbnailKeepList != null) {
        	mergedImgList.addAll(thumbnailKeepList);
        }
        if (instlFileKeepList != null) {
        	mergedImgList.addAll(instlFileKeepList);
        }

        mnulMapper.deleteNotInfileNm(eqpmntId, mergedMnulList);
        amsImgMapper.deleteNotInfileNm(eqpmntId, mergedImgList);
        
        List<String> thumbnailDeletedIds = equipRequest.getThumbnailDelete();
        List<String> mnulDeletedIds = equipRequest.getFilesDelete();
        List<String> dtlImgDeletedIds = equipRequest.getDtlImgDelete();
        List<String> mnulVideoDeletedIds = equipRequest.getVideoFileDelete();
        List<String> instlDeletedIds = equipRequest.getInstlFileDelete();
        //List<String> thumbnailKeepIds = equipRequest.getThumbnailKeep();
        //List<String> mnulKeepIds = equipRequest.getFilesKeep();
        //List<String> dtlImgKeepIds = equipRequest.getDtlImgKeep();
        //List<String> mnulVideoKeepIds = equipRequest.getVideoFileKeep();
        //List<String> instlKeepIds = equipRequest.getInstlFileKeep();

        try {
        	mnulList = objectMapper.readValue(equipRequest.getMnulVoList(), new TypeReference<>() {});
        	installVoList = objectMapper.readValue(equipRequest.getInstallVoList(), new TypeReference<List<InstallVo>>() {});
        	faqList = objectMapper.readValue(equipRequest.getFaqVoList(), new TypeReference<List<FaqVo>>() {});

        } catch (IOException e) {
            System.err.println("ERROR : " + e);
        }
        
        /*
        if (thumbnailDeletedIds != null && !thumbnailDeletedIds.isEmpty()) {
            for (String fileNm : thumbnailDeletedIds) {
                AmsImgVo vo = amsImgMapper.getImgByFileNm(fileNm);
                if (vo != null) {
                    FileUtil.delete(vo.getFilePath(), vo.getFileNm() + "." + vo.getFileExtn());
                }
            }
        }
        if (dtlImgDeletedIds != null && !dtlImgDeletedIds.isEmpty()) {
            for (String fileNm : dtlImgDeletedIds) {
                AmsImgVo vo = amsImgMapper.getImgByFileNm(fileNm);
                if (vo != null) {
                    FileUtil.delete(vo.getFilePath(), vo.getFileNm() + "." + vo.getFileExtn());
                }
            }
        }
        if (mnulDeletedIds != null && !mnulDeletedIds.isEmpty()) {
            for (String fileNm : mnulDeletedIds) {
            	MnulVo vo = mnulMapper.getMnlByFileNm(fileNm);
                if (vo != null) {
                    FileUtil.delete(vo.getFilePath(), vo.getFileNm() + "." + vo.getFileExtn());
                }
            }
        }
        // ✅ 4. 영상 메뉴얼 삭제
        if (mnulDeletedIds != null && !mnulDeletedIds.isEmpty()) {
            for (String fileNm : mnulDeletedIds) {
            	MnulVo vo = mnulMapper.getMnlByFileNm(fileNm);
                if (vo != null) {
                    FileUtil.delete(vo.getFilePath(), vo.getFileNm() + "." + vo.getFileExtn());
                }
            }
        }
        if (instlDeletedIds != null && !instlDeletedIds.isEmpty()) {
            for (String fileNm : instlDeletedIds) {
                AmsImgVo vo = amsImgMapper.getImgByFileNm(fileNm);
                if (vo != null) {
                    FileUtil.delete(vo.getFilePath(), vo.getFileNm() + "." + vo.getFileExtn());
                }
            }
        }
        */
        //1.장비정보
        if (equipRequest.getFiles() != null) {
            List files = new ArrayList<>();
            for (MultipartFile file : equipRequest.getFiles()) {
                MnulVo vo = MnulVo.builder()
                        .file(file)
                        .build();
                files.add(vo);
            }
            mnulService.upsertMnul(files, eqpmntId, "file");
        }

        equipRequest.setMdfrId(new SecurityInfoUtil().getAccountId());
        equipMapper.updateEquip(equipRequest);

        //장비 썸네일, 상세 이미지
        
        if (equipRequest.getDtlImg() != null) {
            amsImgService.saveImgs(equipRequest.getDtlImg(), eqpmntId, null, "detail");
        }

        if (equipRequest.getThumbnail() != null) {
            amsImgService.saveImgs(equipRequest.getThumbnail(), eqpmntId, null, "thumbnail");
        }

        //2.영상메뉴얼
        mnulService.upsertMnulFromUrl(mnulList, eqpmntId);

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
