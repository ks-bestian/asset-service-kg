package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.controller;

import io.swagger.annotations.Api;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "나의정보 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyInfoController {

    private final MyInfoService myInfoService;

}
