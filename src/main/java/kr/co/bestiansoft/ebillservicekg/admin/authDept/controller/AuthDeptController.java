package kr.co.bestiansoft.ebillservicekg.admin.authDept.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.service.AuthDeptService;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "권한별메뉴 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthDeptController {

    private final AuthDeptService authDeptService;
    @ApiOperation(value = "부서별 권한메뉴 조회", notes = "부서별 권한 메뉴를 조회한다.")
    @GetMapping("/admin/authDept/{deptCd}")
    public ResponseEntity<CommonResponse> getAuthDeptList(@PathVariable String deptCd) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authDeptService.getAuthDeptList(deptCd)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서별 메뉴정보 저장", notes = "부서별 메뉴정보를 저장한다.")
    @PostMapping("/admin/authDept")
    public ResponseEntity<CommonResponse> createAuthDept(@RequestBody AuthDeptCreate authDeptCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthDept created successfully", authDeptService.createAuthDept(authDeptCreate)), HttpStatus.CREATED);
    }

}
