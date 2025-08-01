package kr.co.bestiansoft.ebillservicekg.asset.manual.service.impl;

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
import java.util.HashMap;
import java.util.List;
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

                try {
                    String filePath = FileUtil.upload(file, fileUploadDir, fileType, orgFileNm);
                    mnulVo.setFilePath(filePath);
                    mnulVo.setFileNm(fileNm);
                    mnulVo.setOrgnlFileNm(orgFileNm);
                    mnulVo.setFileExtn(file.getContentType());
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
            mnulVo.setMnlLng("KR"); //todo 수정

            Optional.ofNullable(mnulVo.getFileNm2())
                    .ifPresent(mnulVo::setFileNm);

//            Optional.ofNullable(mnulVo.getMnlLng2())
//                    .ifPresent(mnulVo::setMnlLng);
            i++;
        }


        return mnulMapper.createMnul(mnulVoList);
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
    public int upsertMnul(List<MnulVo> mnulVoList, String eqpmntId) {
        for (MnulVo vo : mnulVoList) {
            if (vo.getMnlId() == null || vo.getMnlId().isEmpty()) {
                vo.setMnlId(StringUtil.getMnlUUID());
            }
            vo.setEqpmntId(eqpmntId);

            int i = 1;
            for (MnulVo mnulVo : mnulVoList) {
                if (vo.getMnlId() == null || vo.getMnlId().isEmpty()) {
                    vo.setMnlId(StringUtil.getMnlUUID());
                    vo.setRgtrId(new SecurityInfoUtil().getAccountId());
                } else {
                    vo.setMdfrId(new SecurityInfoUtil().getAccountId());
                }
                vo.setEqpmntId(eqpmntId);


                if (mnulVo.getFile() != null) {
                    MultipartFile file = mnulVo.getFile();
                    String orgFileNm = file.getOriginalFilename();

                    int lastDotIndex = orgFileNm.lastIndexOf('.');
                    String fileNm = orgFileNm.substring(0, lastDotIndex);
                    String fileType = file.getContentType();

                    try {
                        String filePath = FileUtil.upload(file, fileUploadDir, fileType, orgFileNm);
                        mnulVo.setFilePath(filePath);
                        mnulVo.setFileNm(fileNm);
                        mnulVo.setOrgnlFileNm(orgFileNm);
                        mnulVo.setFileExtn(file.getContentType());
                        mnulVo.setFileSz(file.getSize());
                    } catch (IOException e) {
                        throw new RuntimeException("파일저장실패 : " + e);
                    }
                }
                mnulVo.setMdfrId(new SecurityInfoUtil().getAccountId());

                Optional.ofNullable(mnulVo.getFileNm2())
                        .ifPresent(mnulVo::setFileNm);
                i++;
            }

            mnulMapper.upsertMnul(vo);
        }
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
        File videoFile = new File(vo.getFilePath());
        InputStream stream = new FileInputStream(videoFile);
        return new InputStreamResource(stream);
    }

    @Override
    public Resource downloadFile(String fileId){
        return FileUtil.loadFile(fileId);
    }
}
