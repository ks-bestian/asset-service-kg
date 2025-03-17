package kr.co.bestiansoft.ebillservicekg.eas.file.service;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EasFileService {
    int insertEasFile (EasFileVo vo);
}
