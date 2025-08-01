package kr.co.bestiansoft.ebillservicekg.asset.install.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.repository.ComCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo.AmsImgVo;
import kr.co.bestiansoft.ebillservicekg.asset.install.repository.InstallMapper;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.asset.manual.vo.MnulVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstallServiceImpl implements InstallService {

    private final InstallMapper installMapper;
    private final ComCodeMapper comCodeMapper;
    private final AmsImgService amsImgService;

    @Transactional
    @Override
    public void createInstall(List<InstallVo> installVoList, String eqpmntId) {

        for(InstallVo installVo: installVoList) {

            String instlId = StringUtil.getInstlUUUID();
            installVo.setInstlId(instlId);
            installVo.setEqpmntId(eqpmntId);
            installVo.setRgtrId(new SecurityInfoUtil().getAccountId());
            installMapper.insertInstall(installVo);

            if(installVo.getFile() != null) {
                MultipartFile[] files = {installVo.getFile()};
                amsImgService.saveImgs(files, eqpmntId, instlId, "installImg");
            }
        }
    }

    @Override
    public List<InstallVo> getInstallList(String eqpmntId) {
        return installMapper.getInstallList(eqpmntId);
    }

    @Override
    public void deleteInstall(String eqpmntId) {
        installMapper.deleteInstall(eqpmntId);
    }

    @Override
    public void deleteInstlById(List<String> ids) {
        for(String id : ids) {
            installMapper.deleteInstlById(id);
        }
    }

    @Override
    public List<ComCodeVo> getInstlPlace() {
        return comCodeMapper.getInstlPlc();
    }

    @Override
    public int upsertInstl(List<InstallVo> instlList, String eqpmntId) {


//        for(InstallVo installVo: installVoList) {
//
//            installVo.setRgtrId(new SecurityInfoUtil().getAccountId());
//            installMapper.insertInstall(installVo);
//
//            if(installVo.getFile() != null) {
//                MultipartFile[] files = {installVo.getFile()};
//                amsImgService.saveImgs(files, eqpmntId, instlId, "installImg");
//            }
//        }


        for (InstallVo vo : instlList) {
            if(vo.getInstlId() == null || vo.getInstlId().isEmpty()) {
                vo.setInstlId(StringUtil.getInstlUUUID());
                vo.setRgtrId(new SecurityInfoUtil().getAccountId());
            }else {
                vo.setMdfrId(new SecurityInfoUtil().getAccountId());
            }

            if(vo.getFile() != null) {
                MultipartFile[] files = {vo.getFile()};
                vo.setEqpmntId(eqpmntId);
                installMapper.upsertInstl(vo);

                amsImgService.saveImgs(files, eqpmntId, vo.getInstlId(), "installImg");
            }
        }
        return 1;
    }

    @Override
    public Resource instlImgAsResource(String instlId) throws IOException {
        AmsImgVo vo = amsImgService.getImgByInstlId(instlId);
        File videoFile = new File(vo.getFilePath());
        InputStream stream = new FileInputStream(videoFile);
        return new InputStreamResource(stream);
    }
}
