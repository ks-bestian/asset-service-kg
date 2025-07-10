package kr.co.bestiansoft.ebillservicekg.admin.authMenu.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.service.AuthMenuService;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Rights menu API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthMenuController {

    private final AuthMenuService authMenuService;

    @Operation(summary = "Menu inquiry by authority", description = "Search for the menu by permission.")
    @GetMapping("/admin/authMenu/{authId}")
    public ResponseEntity<CommonResponse> getComAuthMenuList(@PathVariable Long authId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authMenuService.getAuthMenuList(authId)), HttpStatus.OK);
    }

    @Operation(summary = "Menu by authority, storage of authority", description = "Auth Menu Create")
    @PostMapping(value = "/admin/authMenu")
    public ResponseEntity<CommonResponse> createAuthMenu(@RequestBody AuthMenuCreate authMenuCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthCode created successfully.", authMenuService.saveAuthMenu(authMenuCreate)), HttpStatus.CREATED);
    }


}
