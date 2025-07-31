package kr.co.bestiansoft.ebillservicekg.asset.install.service;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface InstallService {
   void createInstall(List<InstallVo> installVoList, String eqpmntId);

   List<InstallVo> getInstallList(String eqpmntId);
   void deleteInstall(String eqpmntId);
   void deleteInstlById(List<String> ids);
   List<ComCodeVo>getInstlPlace();
   int upsertInstl(List<InstallVo> instlList, String eqpmntId);
   Resource instlImgAsResource(String instlId) throws IOException;
}
