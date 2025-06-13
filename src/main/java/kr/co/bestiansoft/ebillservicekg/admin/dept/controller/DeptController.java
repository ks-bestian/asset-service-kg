package kr.co.bestiansoft.ebillservicekg.admin.dept.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@Api(tags = "부서 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @ApiOperation(value = "부서리스트 조회", notes = "부서리스트를 조회한다.")
    @GetMapping("admin/dept")
    public ResponseEntity<CommonResponse> getDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서 트리 리스트 조회", notes = "부서 트리 리스트를 조회한다.")
    @GetMapping("admin/dept/tree")
    public ResponseEntity<CommonResponse> getDeptTree(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getDeptTree(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서 상세 조회", notes = "부서를 상세 조회한다.")
    @GetMapping("admin/dept/{deptCd}")
    public ResponseEntity<CommonResponse> getDeptById(@PathVariable String deptCd, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptById(deptCd, lang)), HttpStatus.OK);
    }


    @ApiOperation(value = "부서 생성", notes = "부서를 생성한다.")
    @PostMapping("admin/dept")
    public ResponseEntity<CommonResponse> createDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department created successfully.", deptService.createDept(deptVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서 수정", notes = "부서를 수정한다.")
    @PutMapping("admin/dept")
    public ResponseEntity<CommonResponse> updateDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department updated successfully", deptService.updateDept(deptVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "부서 삭제", notes = "부서를 삭제한다.")
    @DeleteMapping("admin/dept")
    public ResponseEntity<CommonResponse> deletedept(@RequestBody List<String> deptCd) {
        deptService.deleteDept(deptCd);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Department code deleted successfully."), HttpStatus.OK);

    }

    @ApiOperation(value = "위원회 리스트 조회", notes = "위원회 리스트를 조회한다.")
    @GetMapping("admin/cmit")
    public ResponseEntity<CommonResponse> getCmit(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getCmitList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 겸직들 저장", notes = "사용자 겸직을 저장한다.")
    @PostMapping("admin/ccof/users")
    public ResponseEntity<CommonResponse> saveUsersCcofs(@RequestBody HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Cmit created successfully.", deptService.saveUsersCcofs(params)), HttpStatus.OK);
    }

    @ApiOperation(value = "위원회 삭제", notes = "위원회를 삭제한다")
    @DeleteMapping("admin/ccof/{deptCd}")
    public ResponseEntity<CommonResponse> deleteCmit(@PathVariable String deptCd, @RequestBody List<String> userIds) {
        deptService.deleteCmit(deptCd, userIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully"), HttpStatus.OK);
    }

}
