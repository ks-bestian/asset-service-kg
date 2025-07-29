package kr.co.bestiansoft.ebillservicekg.asset.install.repository;

import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InstallMapper {

    int insertInstall(List<InstallVo> installVoList);
    List<InstallVo> getInstallList(String eqpmntId);
    void deleteInstall(String eqpmntId);
    void deleteInstlById(String instlId);
    int upsertInstl(InstallVo vo);
}
