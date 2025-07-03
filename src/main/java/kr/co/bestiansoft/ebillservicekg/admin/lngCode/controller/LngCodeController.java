package kr.co.bestiansoft.ebillservicekg.admin.lngCode.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.LngCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "language API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class LngCodeController {
    private final LngCodeService lngCodeService;


    @ApiOperation(value = "Language code list check", notes = "language List Inquiry.")
    @GetMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> getLngCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Language code generation", notes = "Language code Create.")
    @PostMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> createLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "LngCode created successfully", lngCodeService.createLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Language code correction", notes = "Language code Modify.")
    @PutMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> updateLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "LngCode updated successfully", lngCodeService.updateLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Language code delete", notes = "Language code Delete.")
    @DeleteMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> deleteLngCode(@RequestBody List<Long> lngId) {
        lngCodeService.deleteLngCode(lngId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "LngCode deleted c"), HttpStatus.OK);
    }

    @ApiOperation(value = "Language code particular check", notes = "Language code Details Inquiry.")
    @GetMapping("/admin/lngCode/{lngId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long lngId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeById(lngId)), HttpStatus.OK);
    }

}
