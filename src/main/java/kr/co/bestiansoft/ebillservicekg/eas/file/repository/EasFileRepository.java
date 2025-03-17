package kr.co.bestiansoft.ebillservicekg.eas.file.repository;

import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EasFileRepository {
    int insertEasFile (EasFileVo vo);
}
