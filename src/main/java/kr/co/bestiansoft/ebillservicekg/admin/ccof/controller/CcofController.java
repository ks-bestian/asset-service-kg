package kr.co.bestiansoft.ebillservicekg.admin.ccof.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl.CcofService;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "겸직 API")
public class CcofController {
    private final CcofService ccofService;

    @ApiOperation(value = "사용자 겸직 리스트 조회", notes = "사용자의 겸직 리스트를 조회한다.")
    @GetMapping("admin/ccof")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param) {
        System.out.println("!2");
        System.out.println(param.toString());
        return new ResponseEntity<>(new CommonResponse(200, "ok", ccofService.getCcofList(param)), HttpStatus.OK);
    }
}
