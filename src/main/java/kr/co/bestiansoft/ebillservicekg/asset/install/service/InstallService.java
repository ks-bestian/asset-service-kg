package kr.co.bestiansoft.ebillservicekg.asset.install.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;

public interface InstallService {
   void createInstall(List<InstallVo> installVoList, String eqpmntId);

   List<InstallVo> getInstallList(String eqpmntId);
   void deleteInstall(String eqpmntId);
   void deleteInstlById(List<String> ids);
}
