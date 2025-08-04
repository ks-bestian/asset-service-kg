package kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.impl;


import kr.co.bestiansoft.ebillservicekg.asset.amsImg.repository.AmsImgMapper;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmsImgServiceImpl implements AmsImgService {
    private final AmsImgMapper amsImgMapper;

    @Value("${file.upload.path}")
    private String fileUploadDir;

    @Override
    public List<AmsImgVo> getImgListByEqpmntId(List<String> eqpmntId) {
        return amsImgMapper.getImgListByEqpmntId(eqpmntId);
    }
    
    @Override
    public List<AmsImgVo> getImgListByInstlId(List<String> instlId) {
        return amsImgMapper.getImgListByInstlId(instlId);
    }

    @Override
    public void deleteImg(String eqpmntId) {
        amsImgMapper.deleteImg(eqpmntId);
    }

    @Override
    public int saveImgs(MultipartFile[] files, String eqpmntId, String instlId, String imgSe){
        for(MultipartFile file : files) {
            String orlFileNm = file.getOriginalFilename();
            int lastDot = orlFileNm.lastIndexOf('.');
            String fileNm = orlFileNm.substring(0, lastDot);
            String fileType = file.getContentType();
            String ext = (lastDot != -1) ? orlFileNm.substring(lastDot + 1) : "";
            String uuid = StringUtil.getUUUID();
            try {
                String filePath = FileUtil.upload(file, makeUploadPath("img"), "", uuid+ "." + ext);

                //amsImg save
                AmsImgVo amsImgVo = new AmsImgVo();
                amsImgVo.setImgId(StringUtil.getImgUUUID());
                amsImgVo.setEqpmntId(eqpmntId);
                amsImgVo.setFilePath(makeSavePath("img"));
                amsImgVo.setFileNm(uuid);
                amsImgVo.setOrgnlFileNm(fileNm);
                amsImgVo.setFileExtn(ext);
                amsImgVo.setFileSz(file.getSize());
                amsImgVo.setImgSe(imgSe);
                amsImgVo.setInstlId(instlId);
                amsImgVo.setRgtrId(new SecurityInfoUtil().getAccountId());

                amsImgMapper.saveImg(amsImgVo);
            }catch (IOException e) {
                throw new RuntimeException("파일저장실패 : " +  e);
            }
        }
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


    @Override
    public List<AmsImgVo> getDetailListByEqpmntId(String eqpmntId) {
        return amsImgMapper.getDetailListByEqpmntId(eqpmntId);
    }

    @Override
    public AmsImgVo getImgByInstlId(String instlId) {
        return amsImgMapper.getImgByInstlId(instlId);

    }
    @Override
    public AmsImgVo getImgVoByImgId(String imgId) {
        return amsImgMapper.getImgVoByImgId(imgId);
    }
}
