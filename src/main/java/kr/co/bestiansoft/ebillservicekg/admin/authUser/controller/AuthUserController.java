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

@Api(tags = "권한별사용자 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthUserController {

    private final AuthUserService authUserService;

    @ApiOperation(value = "권한별 사용자 목록 조회", notes = "권한별 사용자 목록을 조회한다.")
    @GetMapping("/admin/authUser/{authId}")
    public ResponseEntity<CommonResponse> getComAuthList(@PathVariable Long authId, @RequestParam HashMap<String, Object> param) {
        param.put("authId", authId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", authUserService.getAuthUserList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "권한별 사용자 생성", notes = "권한별 사용자를 생성한다.")
    @PostMapping(value = "/admin/authUser")
    public ResponseEntity<CommonResponse> createAuthUser(@RequestBody AuthUserVo authUserVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthUsers created successfully", authUserService.createAuthUser(authUserVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "권한별 사용자 삭제", notes = "권한별 사용자를 삭제한다.")
    @DeleteMapping("/admin/authUser")
    public ResponseEntity<CommonResponse> deleteAuthUser(@RequestBody List<Long> authId) {
        authUserService.deleteAuthUser(authId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "AuthUser deleted."), HttpStatus.OK);
    }


}
