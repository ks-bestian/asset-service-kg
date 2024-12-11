package kr.co.bestiansoft.ebillservicekg.admin.dept.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Api(tags = "부서 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @ApiOperation(value = "부서리스트 조회", notes = "부서리스트를 조회한다.")
    @GetMapping("admin/comDept")
    public ResponseEntity<CommonResponse> getComDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서 상세 조회", notes = "부서를 상세 조회한다.")
    @GetMapping("admin/comDept/{deptCd}")
    public ResponseEntity<CommonResponse> getComDeptById(@PathVariable String deptCd) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptById(deptCd)), HttpStatus.OK);
    }

}
