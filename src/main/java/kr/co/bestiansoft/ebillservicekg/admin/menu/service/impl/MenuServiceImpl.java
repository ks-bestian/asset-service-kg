package kr.co.bestiansoft.ebillservicekg.admin.menu.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.menu.domain.MenuInitResponse;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuRepository;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.entity.MenuEntity;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;

    public List<MenuInitResponse> initMenu(String languageType, String accountId) {
        log.info("initMenu Service! >> {}" , accountId);

        if(accountId.equals("admin")) {
            List<MenuEntity> menuEntityList = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ord"));
            return menuEntityList.stream().map(entity -> MenuInitResponse.from(entity, languageType)).collect(Collectors.toList());
        }

        List<MenuEntity> menuEntityList = menuMapper.selectMenuByUserId(accountId);
        return menuEntityList.stream().map(entity -> MenuInitResponse.from(entity, languageType)).collect(Collectors.toList());
    }
}
