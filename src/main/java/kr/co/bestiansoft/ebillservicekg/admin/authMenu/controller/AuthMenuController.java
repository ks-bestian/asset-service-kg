package kr.co.bestiansoft.ebillservicekg.admin.authMenu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.AuthMenuService;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "권한별메뉴 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthMenuController {

    private final AuthMenuService authMenuService;

    @ApiOperation(value = "권한별 메뉴 조회", notes = "권한별 메뉴를 조회한다.")
    @GetMapping("/admin/authMenu/{authId}")
    public ResponseEntity<CommonResponse> getComAuthMenuList(@PathVariable Long authId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authMenuService.getAuthMenuList(authId)), HttpStatus.OK);
    }

    @ApiOperation(value = "권한별 메뉴, 권한 저장", notes = "Auth Menu Create")
    @PostMapping(value = "/admin/authMenu")
    public ResponseEntity<CommonResponse> createAuthMenu(@RequestBody AuthMenuCreate authMenuCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthCode created successfully.", authMenuService.saveAuthMenu(authMenuCreate)), HttpStatus.CREATED);
    }


}
