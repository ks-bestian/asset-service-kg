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


@Api(tags = "department API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @ApiOperation(value = "Department list check", notes = "Department list Inquiry.")
    @GetMapping("admin/dept")
    public ResponseEntity<CommonResponse> getDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "department Tree List check", notes = "department Tree List Inquiry.")
    @GetMapping("admin/dept/tree")
    public ResponseEntity<CommonResponse> getDeptTree(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getDeptTree(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "department particular check", notes = "Department particular Inquiry.")
    @GetMapping("admin/dept/{deptCd}")
    public ResponseEntity<CommonResponse> getDeptById(@PathVariable String deptCd, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptById(deptCd, lang)), HttpStatus.OK);
    }


    @ApiOperation(value = "department generation", notes = "Department Create.")
    @PostMapping("admin/dept")
    public ResponseEntity<CommonResponse> createDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department created successfully.", deptService.createDept(deptVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "department correction", notes = "Department Modify.")
    @PutMapping("admin/dept")
    public ResponseEntity<CommonResponse> updateDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department updated successfully", deptService.updateDept(deptVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "department delete", notes = "Department Delete.")
    @DeleteMapping("admin/dept")
    public ResponseEntity<CommonResponse> deletedept(@RequestBody List<String> deptCd) {
        deptService.deleteDept(deptCd);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Department code deleted successfully."), HttpStatus.OK);

    }

    @ApiOperation(value = "committee List check", notes = "committee List Inquiry.")
    @GetMapping("admin/cmit")
    public ResponseEntity<CommonResponse> getCmit(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getCmitList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Save user concurrent positions.", notes = "Save user's multiple department assignments.")
    @PostMapping("admin/ccof/users")
    public ResponseEntity<CommonResponse> saveUsersCcofs(@RequestBody HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Cmit created successfully.", deptService.saveUsersCcofs(params)), HttpStatus.OK);
    }

    @ApiOperation(value = "committee delete", notes = "Committee Delete")
    @DeleteMapping("admin/ccof/{deptCd}")
    public ResponseEntity<CommonResponse> deleteCmit(@PathVariable String deptCd, @RequestBody List<String> userIds) {
        deptService.deleteCmit(deptCd, userIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully"), HttpStatus.OK);
    }

}
