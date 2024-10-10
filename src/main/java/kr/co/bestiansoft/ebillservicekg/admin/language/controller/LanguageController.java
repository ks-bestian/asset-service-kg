package kr.co.bestiansoft.ebillservicekg.admin.language.controller;

import kr.co.bestiansoft.ebillservicekg.admin.language.domain.LanguageResponse;
import kr.co.bestiansoft.ebillservicekg.admin.language.service.LanguageService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/api/languages")
    public ResponseEntity<ListResponse<LanguageResponse>> initLanguage() {

        ListResponse<LanguageResponse> languageList = languageService.selectLanguages();

        return new ResponseEntity<>(languageList, HttpStatus.OK);
    }
}
