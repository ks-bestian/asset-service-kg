package kr.co.bestiansoft.ebillservicekg.admin.authUser.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.service.AuthUserService;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "Authority User API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthUserController {

    private final AuthUserService authUserService;

    @ApiOperation(value = "Inquiry for user list by authority", notes = "View a list of users by permission.")
    @GetMapping("/admin/authUser/{authId}")
    public ResponseEntity<CommonResponse> getComAuthList(@PathVariable Long authId, @RequestParam HashMap<String, Object> param) {
        param.put("authId", authId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", authUserService.getAuthUserList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Create user by authority", notes = "Create a user by authority.")
    @PostMapping(value = "/admin/authUser")
    public ResponseEntity<CommonResponse> createAuthUser(@RequestBody AuthUserVo authUserVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthUsers created successfully", authUserService.createAuthUser(authUserVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete user by authority", notes = "Delete users by authority.")
    @DeleteMapping("/admin/authUser")
    public ResponseEntity<CommonResponse> deleteAuthUser(@RequestBody List<Long> authId) {
        authUserService.deleteAuthUser(authId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "AuthUser deleted."), HttpStatus.OK);
    }


}
