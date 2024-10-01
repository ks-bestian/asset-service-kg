package kr.co.bestiansoft.ebillservicekg.admin.menu.controller;

import kr.co.bestiansoft.ebillservicekg.admin.menu.domain.MenuInitResponse;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/api/menus/init/{languageType}")
    public MenuInitResponse initMenu(@PathVariable  String languageType) {

        return menuService.initMenu();
    }
}
