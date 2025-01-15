package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.MtngFromService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "회의 예정 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngFromController {

    private final MtngFromService mtngFromService;

    @ApiOperation(value = "회의 예정 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/from")
    public ResponseEntity<CommonResponse> getMtngFromList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/mtng/from/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngFromById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromById(mtngId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "회의 예정 등록", notes = "회의 예정 등록")
    @PostMapping(value = "/bill/mtng/from")
    public ResponseEntity<CommonResponse> createMtngFrom(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.createMtngFrom(mtngFromVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "회의 예정 - 의원 리스트 조회", notes = "회의 예정 - 의원 리스트를 조회한다.")
    @GetMapping("/bill/mtng/from/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.getMemberList(param)), HttpStatus.OK);
    }

}