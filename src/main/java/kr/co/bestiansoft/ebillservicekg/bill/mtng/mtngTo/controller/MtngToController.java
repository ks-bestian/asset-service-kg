package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.MtngToService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "회의 결과 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngToController {

    private final MtngToService mtngToService;

    @ApiOperation(value = "회의 결과 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/to")
    public ResponseEntity<CommonResponse> getMtngToList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngToService.getMtngToList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 결과 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/mtng/to/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngToById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngToService.getMtngToById(mtngId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "회의 결과 등록", notes = "회의 결과 등록")
    @PostMapping(value = "/bill/mtng/to", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtngTo(MtngToVo mtngToVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngToService.createMtngTo(mtngToVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "회의 결과 - 의원 리스트 조회", notes = "회의 결과 - 의원 리스트를 조회한다.")
    @GetMapping("/bill/mtng/to/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngToService.getMemberList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "회의 결과 - 회의 취소", notes = "회의 취소")
    @DeleteMapping("/bill/mtng/to")
    public ResponseEntity<CommonResponse> deleteMtngTo(@RequestBody List<Long> mtngIds) {
    	mtngToService.deleteMtng(mtngIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "meeting deleted successfully"), HttpStatus.OK);
    }

}