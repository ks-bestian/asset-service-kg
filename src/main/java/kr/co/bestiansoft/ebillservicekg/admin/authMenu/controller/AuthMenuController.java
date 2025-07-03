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

@Api(tags = "Rights menu API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthMenuController {

    private final AuthMenuService authMenuService;

    @ApiOperation(value = "Menu inquiry by authority", notes = "Search for the menu by permission.")
    @GetMapping("/admin/authMenu/{authId}")
    public ResponseEntity<CommonResponse> getComAuthMenuList(@PathVariable Long authId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authMenuService.getAuthMenuList(authId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Menu by authority, storage of authority", notes = "Auth Menu Create")
    @PostMapping(value = "/admin/authMenu")
    public ResponseEntity<CommonResponse> createAuthMenu(@RequestBody AuthMenuCreate authMenuCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthCode created successfully.", authMenuService.saveAuthMenu(authMenuCreate)), HttpStatus.CREATED);
    }


}
