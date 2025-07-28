package kr.co.bestiansoft.ebillservicekg.asset.install.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.asset.install.repository.InstallMapper;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstallServiceImpl implements InstallService {

    private final InstallMapper installMapper;

    @Transactional
    @Override
    public void createInstall(List<InstallVo> installVoList, String eqpmntId) {

        for(InstallVo installVo: installVoList) {
            String instlId = StringUtil.getInstlUUUID();
            installVo.setInstlId(instlId);
            installVo.setEqpmntId(eqpmntId);
            installVo.setRgtrId(new SecurityInfoUtil().getAccountId());

        }
        installMapper.insertInstall(installVoList);

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
}
