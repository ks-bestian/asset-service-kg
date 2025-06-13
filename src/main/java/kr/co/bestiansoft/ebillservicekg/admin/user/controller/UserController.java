package kr.co.bestiansoft.ebillservicekg.admin.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "직원관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "직원 리스트 조회", notes = "직원 리스트를 조회한다.")
    @GetMapping("admin/user")
    public ResponseEntity<CommonResponse> getUserList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "직원상세 조회", notes = "직원 상세를 조회한다.")
    @GetMapping("admin/user/{seq}")
    public ResponseEntity<CommonResponse> getUserDetail(@PathVariable Long seq) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserDetail(seq)), HttpStatus.OK);
    }

    @ApiOperation(value = "직원 생성", notes = "직원를 생성한다.")
    @PostMapping("admin/user")
    public ResponseEntity<CommonResponse> createUser(@RequestBody UserVo userVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "User created successfully.", userService.createUser(userVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "직원 수정", notes = "직원을 수정한다.")
    @PutMapping("admin/user")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody UserVo userVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "User updated successfully", userService.updateUser(userVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "직원 삭제", notes = "직원을 삭제한다.")
    @DeleteMapping("admin/user")
    public ResponseEntity<CommonResponse> deleteUser(@RequestBody List<String> ids) {
        userService.deleteUser(ids);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "User code deleted successfully."), HttpStatus.OK);
    }

    //부서별 사용자 조회(겸직, 위원회, 정당 포함) -- 부서관리, 권한별사용자관리
    @ApiOperation(value = "부서별 사용자 조회", notes = "부서별사용자를 조회한다.")
    @GetMapping("user/dept")
    public ResponseEntity<CommonResponse> getUserByDept(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserByDept(param)), HttpStatus.OK);
    }


    @ApiOperation(value = "사용자 비밀번호 초기화", notes = "사용자 비밀번호 초기화")
    @PutMapping("user/reset/pswd")
    public ResponseEntity<CommonResponse> resetPswd(@RequestBody HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.resetPswd(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 비밀번호 수정", notes = "사용자 비밀번호를 수정한다.")
    @PutMapping("login/update")
    public ResponseEntity<CommonResponse> updatePswd(@RequestBody HashMap<String, Object> param) {
        userService.updatePswd(param);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "updated successfully"), HttpStatus.OK);
    }



    @ApiOperation(value = "직급 수정", notes = "직급을 수정한다.")
    @PutMapping("admin/user/job")
    public ResponseEntity<CommonResponse> updateJob(@RequestBody HashMap<String, Object> param) {
        userService.updateJob(param);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "updated successfully"), HttpStatus.OK);
    }


}
