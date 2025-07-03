package kr.co.bestiansoft.ebillservicekg.admin.baseCode.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "Age code management API")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BaseCodeController {

    private final BaseCodeService basecodeService;

    @ApiOperation(value = "Code list check", notes = "Age Codes List Inquiry.")
    @GetMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> getBaseCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Age code generation", notes = "Age code Create.")
    @PostMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> createBaseCode(@RequestBody BaseCodeVo baseCodeVo) {

    	BaseCodeVo returnVo = basecodeService.createBaseCode(baseCodeVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "BaseCode created successfully", returnVo), HttpStatus.CREATED);

    }

    @ApiOperation(value = "Age Code correction", notes = "Age Code modify.")
    @PutMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> updateBaseCode(@RequestBody BaseCodeVo baseCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "BaseCode updated successfully", basecodeService.updateBaseCode(baseCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Age Code delete", notes = "Age Code Delete.")
    @DeleteMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> deleteBaseCode(@RequestBody List<String> ids) {
        basecodeService.deleteBaseCode(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "BaseCode deleted successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "Code particular check", notes = "Code Details Inquiry.")
    @GetMapping("/admin/baseCode/{baseCode}")
    public ResponseEntity<CommonResponse> getBaseCodeByCd(@PathVariable Long baseCode) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeById(baseCode)), HttpStatus.OK);
    }
}
