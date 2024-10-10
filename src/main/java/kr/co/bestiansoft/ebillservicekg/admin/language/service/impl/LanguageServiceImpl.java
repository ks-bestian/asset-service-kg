package kr.co.bestiansoft.ebillservicekg.admin.language.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.language.domain.LanguageResponse;
import kr.co.bestiansoft.ebillservicekg.admin.language.repository.LanguageMapper;
import kr.co.bestiansoft.ebillservicekg.admin.language.service.LanguageService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageMapper languageMapper;

    @Override
    public ListResponse<LanguageResponse> selectLanguages() {
        List<LanguageResponse> languageList = languageMapper.selectLanguages();
        Long count = languageMapper.selectLanguageCount();


        return new ListResponse<>(count, languageList);
    }
}
