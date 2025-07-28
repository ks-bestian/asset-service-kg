package kr.co.bestiansoft.ebillservicekg.admin.baseCode.controller;


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
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Age code management API")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BaseCodeController {

    private final BaseCodeService basecodeService;

    @Operation(summary = "Code list check", description = "Age Codes List Inquiry.")
    @GetMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> getBaseCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Age code generation", description = "Age code Create.")
    @PostMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> createBaseCode(@RequestBody BaseCodeVo baseCodeVo) {

    	BaseCodeVo returnVo = basecodeService.createBaseCode(baseCodeVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "BaseCode created successfully", returnVo), HttpStatus.CREATED);

    }

    @Operation(summary = "Age Code correction", description = "Age Code modify.")
    @PutMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> updateBaseCode(@RequestBody BaseCodeVo baseCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "BaseCode updated successfully", basecodeService.updateBaseCode(baseCodeVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Age Code delete", description = "Age Code Delete.")
    @DeleteMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> deleteBaseCode(@RequestBody List<String> ids) {
        basecodeService.deleteBaseCode(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "BaseCode deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Code particular check", description = "Code Details Inquiry.")
    @GetMapping("/admin/baseCode/{baseCode}")
    public ResponseEntity<CommonResponse> getBaseCodeByCd(@PathVariable Long baseCode) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeById(baseCode)), HttpStatus.OK);
    }
}
