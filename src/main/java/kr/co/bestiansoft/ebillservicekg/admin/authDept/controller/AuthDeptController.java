package kr.co.bestiansoft.ebillservicekg.admin.authDept.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.service.AuthDeptService;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Rights menu API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthDeptController {

    private final AuthDeptService authDeptService;
    @Operation(summary = "Inquiry of authority menu by department", description = "Search for the authority menu by department.")
    @GetMapping("/admin/authDept/{deptCd}")
    public ResponseEntity<CommonResponse> getAuthDeptList(@PathVariable String deptCd) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authDeptService.getAuthDeptList(deptCd)), HttpStatus.OK);
    }

    @Operation(summary = "Save menu information by department", description = "Save menu information for each department.")
    @PostMapping("/admin/authDept")
    public ResponseEntity<CommonResponse> createAuthDept(@RequestBody AuthDeptCreate authDeptCreate) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthDept created successfully", authDeptService.createAuthDept(authDeptCreate)), HttpStatus.CREATED);
    }

}
