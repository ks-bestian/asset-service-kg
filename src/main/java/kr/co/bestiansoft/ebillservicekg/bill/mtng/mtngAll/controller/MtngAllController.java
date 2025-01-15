package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.MtngAllService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "회의 전체 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngAllController {

    private final MtngAllService mtngAllService;

    @ApiOperation(value = "회의 전체 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/all")
    public ResponseEntity<CommonResponse> getMtngList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.getMtngList(param)), HttpStatus.OK);
    }


    @ApiOperation(value = "회의 전체 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/mtng/all/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngById(@PathVariable String mtngId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.getMtngById(mtngId)), HttpStatus.OK);
    }

}