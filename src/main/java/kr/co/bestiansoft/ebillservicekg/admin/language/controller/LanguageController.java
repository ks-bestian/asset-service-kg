package kr.co.bestiansoft.ebillservicekg.admin.language.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LanguageController {


    @GetMapping("/api/languages")
    public void initLanguage() {

    }
}
