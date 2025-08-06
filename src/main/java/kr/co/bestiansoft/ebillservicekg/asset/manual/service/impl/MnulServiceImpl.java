package kr.co.bestiansoft.ebillservicekg.asset.manual.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.asset.manual.repository.MnulMapper;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MnulServiceImpl implements MnulService {
    private final MnulMapper mnulMapper;
    @Value("${file.upload.path}")
    private String fileUploadDir;

    @Override
    public int createMnul(List<MnulVo> mnulVoList, String eqpmntId, String mnlSe) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
        	if (mnulVo.getFile() != null) {
            
        		MultipartFile file = mnulVo.getFile();
            
        		String orgFileNm = file.getOriginalFilename();
            
                int lastDotIndex = orgFileNm.lastIndexOf('.');
                String fileNm = orgFileNm.substring(0, lastDotIndex);
                String fileType = file.getContentType();
                String ext = (lastDotIndex != -1) ? orgFileNm.substring(lastDotIndex + 1) : "";
                String uuid = StringUtil.getUUUID();

                try {
                    String filePath = FileUtil.upload(file, makeUploadPath("mnl"), "", uuid+ "." + ext);
                    mnulVo.setFilePath(makeSavePath("mnl"));
                    mnulVo.setFileNm(uuid);
                    mnulVo.setOrgnlFileNm(fileNm);
                    mnulVo.setFileExtn(ext);
                    mnulVo.setFileSz(file.getSize());
                } catch (IOException e) {
                    throw new RuntimeException("파일저장실패 : " + e);
                }
            }

            mnulVo.setMnlId(StringUtil.getMnlUUID());
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setMnlSe(mnlSe);
            mnulVo.setSeq(i);
            mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);

//            Optional.ofNullable(mnulVo.getMnlLng2())
//                    .ifPresent(mnulVo::setMnlLng);
            i++;
        }

        System.out.println("mnulVoList :: "+mnulVoList.size());
        return mnulMapper.createMnul(mnulVoList);
    }

    @Override
    public int createMnulFromUrl(List<MnulVo> mnulVoList, String eqpmntId) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
            String videoUrl = null;
            if (mnulVo.getVideoFileUrl() != null && !mnulVo.getVideoFileUrl().isEmpty()) {
                videoUrl = mnulVo.getVideoFileUrl().get(0); // 현재는 한 개만 사용한다고 가정
            }
            // UUID 추출
            String uuid = videoUrl.substring(videoUrl.lastIndexOf("/") + 1);

            mnulVo.setMnlId(StringUtil.getMnlUUID());
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setMnlSe("video");
            mnulVo.setSeq(i++);
            mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());

            mnulVo.setFilePath(makeSavePath("mnl"));
            mnulVo.setFileNm(uuid);

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);
        }
        
        for (MnulVo vo : mnulVoList) {
            System.out.println("🧾 MnulVo:");
            System.out.println("  mnlId: " + vo.getMnlId());
            System.out.println("  eqpmntId: " + vo.getEqpmntId());
            System.out.println("  mnlSe: " + vo.getMnlSe());
            System.out.println("  seq: " + vo.getSeq());
            System.out.println("  filePath: " + vo.getFilePath());
            System.out.println("  fileNm: " + vo.getFileNm());
            System.out.println("  orgnlFileNm: " + vo.getOrgnlFileNm());
            System.out.println("  fileExtn: " + vo.getFileExtn());
            System.out.println("  fileSz: " + vo.getFileSz());
            System.out.println("  mnlLng: " + vo.getMnlLng());
            System.out.println("  mnlNm: " + vo.getMnlNm());
            System.out.println("  rgtrId: " + vo.getRgtrId());
        }
        System.out.println("mnulVoList :: "+mnulVoList.size());
        return mnulMapper.createMnul(mnulVoList);
    }
    
    @Override
    public int upsertMnulFromUrl(List<MnulVo> mnulVoList, String eqpmntId) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
            String videoUrl = null;
            if (mnulVo.getVideoFileUrl() != null && !mnulVo.getVideoFileUrl().isEmpty()) {
                videoUrl = mnulVo.getVideoFileUrl().get(0); // 현재는 한 개만 사용한다고 가정
            }
            // UUID 추출
            String uuid = videoUrl != null ? videoUrl.substring(videoUrl.lastIndexOf("/") + 1) : null;

            if (mnulVo.getMnlId() == null || mnulVo.getMnlId().isEmpty()) {
                mnulVo.setMnlId(StringUtil.getMnlUUID());
                mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());
            } else {
                mnulVo.setMdfrId(new SecurityInfoUtil().getAccountId());
            }
            
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setMnlSe("video");
            mnulVo.setSeq(i++);
            mnulVo.setFilePath(makeSavePath("mnl"));
            mnulVo.setFileNm(uuid);

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);
            
            mnulMapper.upsertMnul(mnulVo);
        }
        
    	List<String> currentIds = mnulVoList.stream()
    		    .map(MnulVo::getMnlId)
    		    .filter(Objects::nonNull)
    		    .toList();

    	mnulMapper.deleteNotIn(eqpmntId, currentIds);

        return 1;
    }
    
    public String makeUploadPath(String middleDir) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String savePath = Paths.get(fileUploadDir, middleDir, today).toString();
        return savePath;
    }
    
    public String makeSavePath(String middleDir) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String savePath = Paths.get(middleDir, today).toString();
        return savePath;
    }

    public void saveMultipartFiles(MultipartFile[] files) {
        String fileId = null;
    }

    @Override
    public List<MnulVo> getMnulListByEquipIds(List<String> eqpmntIds) {

        return mnulMapper.getMnulListByEquipIds(eqpmntIds);
    }

    @Override
    public List<MnulVo> getMnulListByEqpmntId(String eqpmntId, String videoYn) {
        HashMap<String, String> params = new HashMap<>();
        params.put("eqpmntId", eqpmntId);
        params.put("videoYn", videoYn);
        return mnulMapper.getMnulListByEqpmntId(params);
    }

    @Override
    public MnulVo getMnlByMnlId(String mnlId) {
        return mnulMapper.getVideoByMnlId(mnlId);
    }

    @Override
    public void deleteMnul(String eqpmntId) {
        mnulMapper.deleteMnul(eqpmntId);
    }

    @Override
    public void deleteMnulById(List<String> ids) {
        for (String id : ids) {
            mnulMapper.deleteMnulById(id);
        }
    }

    @Override
    public int upsertMnul(List<MnulVo> mnulVoList, String eqpmntId, String mnlSe) {
        int i = 1;
        for (MnulVo mnulVo : mnulVoList) {
        	if (mnulVo.getFile() != null) {
            
        		MultipartFile file = mnulVo.getFile();
            
        		String orgFileNm = file.getOriginalFilename();
            
                int lastDotIndex = orgFileNm.lastIndexOf('.');
                String fileNm = orgFileNm.substring(0, lastDotIndex);
                String fileType = file.getContentType();
                String ext = (lastDotIndex != -1) ? orgFileNm.substring(lastDotIndex + 1) : "";
                String uuid = StringUtil.getUUUID();

                try {
                    String filePath = FileUtil.upload(file, makeUploadPath("mnl"), "", uuid+ "." + ext);
                    mnulVo.setFilePath(makeSavePath("mnl"));
                    mnulVo.setFileNm(uuid);
                    mnulVo.setOrgnlFileNm(fileNm);
                    mnulVo.setFileExtn(ext);
                    mnulVo.setFileSz(file.getSize());
                } catch (IOException e) {
                    throw new RuntimeException("파일저장실패 : " + e);
                }
            }

		    if (mnulVo.getMnlId() == null || mnulVo.getMnlId().isEmpty()) {
	            mnulVo.setMnlId(StringUtil.getMnlUUID());
		        mnulVo.setRgtrId(new SecurityInfoUtil().getAccountId());
	        } else {
	        	mnulVo.setMdfrId(new SecurityInfoUtil().getAccountId());
		    } 
            mnulVo.setEqpmntId(eqpmntId);
            mnulVo.setMnlSe(mnlSe);
            mnulVo.setSeq(i);
            

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);
            
            mnulMapper.upsertMnul(mnulVo);

            
            i++;
        }

    	List<String> currentIds = mnulVoList.stream()
    		    .map(MnulVo::getMnlId)
    		    .filter(Objects::nonNull)
    		    .toList();

    	mnulMapper.deleteNotIn(eqpmntId, currentIds);

        return 1;
    }

    @Override
    public Resource loadVideoAsResource(String eqpmntId) throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("eqpmntId", eqpmntId);
        params.put("videoYn", "Y");
        List<MnulVo> list = mnulMapper.getMnulListByEqpmntId(params);
        Optional<MnulVo> vo = list.stream().filter(item -> "video".equals(item.getMnlSe())).findFirst();
        File videoFile = new File(vo.get().getFilePath());
        InputStream stream = new FileInputStream(videoFile);
        return new InputStreamResource(stream);
    }

    @Override
    public Resource videoMnlAsResource(String mnlId) throws IOException {
        MnulVo vo = mnulMapper.getVideoByMnlId(mnlId);
        File videoFile = new File(makeLoadPath(vo));
        
        InputStream stream = new FileInputStream(videoFile);
        return new InputStreamResource(stream);
    }
    
    public String makeLoadPath(MnulVo vo) {
        String fileNameWithExt = vo.getFileExtn() != null && !vo.getFileExtn().isBlank()
                ? vo.getFileNm() + "." + vo.getFileExtn()
                : vo.getFileNm();
        String loadPath = Paths.get(fileUploadDir, vo.getFilePath(), fileNameWithExt).toString();
        return loadPath;
    }

    @Override
    public Resource downloadFile(String fileId){
    	String savePath = Paths.get(fileUploadDir, fileId).toString();
        return FileUtil.loadFile(savePath);
    }
}
