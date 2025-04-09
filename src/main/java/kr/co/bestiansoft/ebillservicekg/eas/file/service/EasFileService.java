package kr.co.bestiansoft.ebillservicekg.eas.file.service;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface EasFileService {
    EasFileVo uploadEasFile (EasFileVo vo);
    int saveEasFile(EasFileVo vo);
}
