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

@Api(tags = "Rights menu API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthDeptController {

    private final AuthDeptService authDeptService;
    @ApiOperation(value = "Inquiry of authority menu by department", notes = "Search for the authority menu by department.")
    @GetMapping("/admin/authDept/{deptCd}")
    public ResponseEntity<CommonResponse> getAuthDeptList(@PathVariable String deptCd) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authDeptService.getAuthDeptList(deptCd)), HttpStatus.OK);
    }

    @ApiOperation(value = "Save menu information by department", notes = "Save menu information for each department.")
    @PostMapping("/admin/authDept")
    public ResponseEntity<CommonResponse> createAuthDept(@RequestBody AuthDeptCreate authDeptCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthDept created successfully", authDeptService.createAuthDept(authDeptCreate)), HttpStatus.CREATED);
    }

}
