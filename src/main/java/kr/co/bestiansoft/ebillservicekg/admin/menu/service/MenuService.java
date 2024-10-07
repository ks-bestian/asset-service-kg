package kr.co.bestiansoft.ebillservicekg.admin.menu.service;

import kr.co.bestiansoft.ebillservicekg.admin.menu.domain.MenuInitResponse;

import java.util.List;

public interface MenuService {

    List<MenuInitResponse> initMenu(String languageType, String accountId);
}
