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

@Api(tags = "언어 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class LngCodeController {
    private final LngCodeService lngCodeService;


    @ApiOperation(value = "언어코드리스트 조회", notes = "언어 리스트를 조회한다.")
    @GetMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> getLngCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "언어코드 생성", notes = "언어코드 생성한다.")
    @PostMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> createLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "LngCode created successfully", lngCodeService.createLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "언어코드 수정", notes = "언어코드를 수정한다.")
    @PutMapping(value = "/admin/lngCode")
    public ResponseEntity<CommonResponse> updateLngCode(@RequestBody LngCodeVo lngCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "LngCode updated successfully", lngCodeService.updateLngCode(lngCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "언어코드 삭제", notes = "언어코드를 삭제한다.")
    @DeleteMapping("/admin/lngCode")
    public ResponseEntity<CommonResponse> deleteLngCode(@RequestBody Long lngId) {
        lngCodeService.deleteLngCode(lngId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "LngCode deleted c"), HttpStatus.OK);
    }

    @ApiOperation(value = "언어코드 상세 조회", notes = "대별코드 상세를 조회한다.")
    @GetMapping("/admin/lngCode/{lngId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long lngId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", lngCodeService.getLngCodeById(lngId)), HttpStatus.OK);
    }

}
