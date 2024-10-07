package kr.co.bestiansoft.ebillservicekg.admin.language.repository;

import kr.co.bestiansoft.ebillservicekg.admin.language.domain.LanguageResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LanguageMapper {

    List<LanguageResponse> selectLanguages();
    Long selectLanguageCount();
}
