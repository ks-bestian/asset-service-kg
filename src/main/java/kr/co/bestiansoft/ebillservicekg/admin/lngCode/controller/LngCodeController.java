package kr.co.bestiansoft.ebillservicekg.admin.lngCode.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "language API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class LngCodeController {
    private final LngCodeService lngCodeService;


    @Operation(summary = "Language code list check", description = "language List Inquiry.")
    @GetMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> getLngCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Language code generation", description = "Language code Create.")
    @PostMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> createLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "LngCode created successfully", lngCodeService.createLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Language code correction", description = "Language code Modify.")
    @PutMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> updateLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "LngCode updated successfully", lngCodeService.updateLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Language code delete", description = "Language code Delete.")
    @DeleteMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> deleteLngCode(@RequestBody List<Long> lngId) {
        lngCodeService.deleteLngCode(lngId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "LngCode deleted c"), HttpStatus.OK);
    }

    @Operation(summary = "Language code particular check", description = "Language code Details Inquiry.")
    @GetMapping("/admin/lngCode/{lngId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long lngId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeById(lngId)), HttpStatus.OK);
    }

}
