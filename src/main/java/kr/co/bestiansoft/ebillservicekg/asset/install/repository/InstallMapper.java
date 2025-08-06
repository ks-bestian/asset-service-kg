package kr.co.bestiansoft.ebillservicekg.asset.install.repository;

import kr.co.bestiansoft.ebillservicekg.asset.install.vo.InstallVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InstallMapper {

    int insertInstall(InstallVo installVoList);
    List<InstallVo> getInstallList(String eqpmntId);
    void deleteInstall(String eqpmntId);
    void deleteInstlById(String instlId);
    void deleteNotIn(@Param("eqpmntId") String eqpmntId, @Param("instlIdList") List<String> instlIdList);
    int upsertInstl(InstallVo vo);
}
