package kr.co.bestiansoft.ebillservicekg.admin.dept.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;


@Tag(name = "department API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class DeptController {
    private final DeptService deptService;

    @Operation(summary = "Department list check", description = "Department list Inquiry.")
    @GetMapping("admin/dept")
    public ResponseEntity<CommonResponse> getDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptList(param)), HttpStatus.OK);
    }

    @Operation(summary = "department Tree List check", description = "department Tree List Inquiry.")
    @GetMapping("admin/dept/tree")
    public ResponseEntity<CommonResponse> getDeptTree(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getDeptTree(param)), HttpStatus.OK);
    }

    @Operation(summary = "department particular check", description = "Department particular Inquiry.")
    @GetMapping("admin/dept/{deptCd}")
    public ResponseEntity<CommonResponse> getDeptById(@PathVariable String deptCd, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getComDeptById(deptCd, lang)), HttpStatus.OK);
    }


    @Operation(summary = "department generation", description = "Department Create.")
    @PostMapping("admin/dept")
    public ResponseEntity<CommonResponse> createDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department created successfully.", deptService.createDept(deptVo)), HttpStatus.OK);
    }

    @Operation(summary = "department correction", description = "Department Modify.")
    @PutMapping("admin/dept")
    public ResponseEntity<CommonResponse> updateDept(@RequestBody DeptVo deptVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Department updated successfully", deptService.updateDept(deptVo)), HttpStatus.OK);
    }

    @Operation(summary = "department delete", description = "Department Delete.")
    @DeleteMapping("admin/dept")
    public ResponseEntity<CommonResponse> deletedept(@RequestBody List<String> deptCd) {
        deptService.deleteDept(deptCd);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Department code deleted successfully."), HttpStatus.OK);

    }

    @Operation(summary = "committee List check", description = "committee List Inquiry.")
    @GetMapping("admin/cmit")
    public ResponseEntity<CommonResponse> getCmit(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", deptService.getCmitList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Save user concurrent positions.", description = "Save user's multiple department assignments.")
    @PostMapping("admin/ccof/users")
    public ResponseEntity<CommonResponse> saveUsersCcofs(@RequestBody HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Cmit created successfully.", deptService.saveUsersCcofs(params)), HttpStatus.OK);
    }

    @Operation(summary = "committee delete", description = "Committee Delete")
    @DeleteMapping("admin/ccof/{deptCd}")
    public ResponseEntity<CommonResponse> deleteCmit(@PathVariable String deptCd, @RequestBody List<String> userIds) {
        deptService.deleteCmit(deptCd, userIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully"), HttpStatus.OK);
    }

}
