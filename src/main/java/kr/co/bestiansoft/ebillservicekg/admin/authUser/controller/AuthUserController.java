package kr.co.bestiansoft.ebillservicekg.admin.authUser.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.service.AuthUserService;
import kr.co.bestiansoft.ebillservicekg.admin.authUser.vo.AuthUserVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authority User API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthUserController {

    private final AuthUserService authUserService;

    @Operation(summary = "Inquiry for user list by authority", description = "View a list of users by permission.")
    @GetMapping("/admin/authUser/{authId}")
    public ResponseEntity<CommonResponse> getComAuthList(@PathVariable Long authId, @RequestParam HashMap<String, Object> param) {
        param.put("authId", authId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", authUserService.getAuthUserList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Create user by authority", description = "Create a user by authority.")
    @PostMapping(value = "/admin/authUser")
    public ResponseEntity<CommonResponse> createAuthUser(@RequestBody AuthUserVo authUserVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthUsers created successfully", authUserService.createAuthUser(authUserVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete user by authority", description = "Delete users by authority.")
    @DeleteMapping("/admin/authUser")
    public ResponseEntity<CommonResponse> deleteAuthUser(@RequestBody List<Long> authId) {
        authUserService.deleteAuthUser(authId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "AuthUser deleted."), HttpStatus.OK);
    }


}
