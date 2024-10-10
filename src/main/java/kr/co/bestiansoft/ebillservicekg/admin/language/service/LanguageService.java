package kr.co.bestiansoft.ebillservicekg.admin.language.service;

import kr.co.bestiansoft.ebillservicekg.admin.language.domain.LanguageResponse;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;

public interface LanguageService {

    ListResponse<LanguageResponse> selectLanguages();
}
