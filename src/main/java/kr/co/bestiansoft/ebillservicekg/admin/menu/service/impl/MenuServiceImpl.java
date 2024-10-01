package kr.co.bestiansoft.ebillservicekg.admin.menu.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.menu.domain.MenuInitResponse;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    public MenuInitResponse initMenu() {
        System.out.println("initMenu");

        return new MenuInitResponse();
    }
}
