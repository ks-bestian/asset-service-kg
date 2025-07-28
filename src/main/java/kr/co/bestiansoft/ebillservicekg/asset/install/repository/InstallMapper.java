package kr.co.bestiansoft.ebillservicekg.asset.install.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;

@Mapper
public interface InstallMapper {

    int insertInstall(List<InstallVo> installVoList);
    List<InstallVo> getInstallList(String eqpmntId);
    void deleteInstall(String eqpmntId);
    void deleteInstlById(String instlId);
}
