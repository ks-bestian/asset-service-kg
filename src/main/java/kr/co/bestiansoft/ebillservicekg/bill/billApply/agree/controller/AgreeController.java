package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건동의 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AgreeController {

    private final AgreeService agreeService;

    @ApiOperation(value = "동의서명 목록", notes = "동의서명 목록을 조회")
    @GetMapping("/bill/agree")
    public ResponseEntity<CommonResponse> getApplyList(@RequestParam HashMap<String, Object> param) {
		// TODO :: 대수 검색조건 설정 필요(현재 14로 하드코딩)
        return new ResponseEntity<>(new CommonResponse(200, "OK", agreeService.getAgreeList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "동의서명 상세", notes = "동의서명 상세를 조회")
    @GetMapping("/bill/agree/{billId}")
    public ResponseEntity<CommonResponse> getApplyDetail(@PathVariable String billId, @RequestParam String userId) {
    	//todo :: 로그인 유저 아이디 가져오는걸로 변경 필요
    	return new ResponseEntity<>(new CommonResponse(200, "OK", agreeService.getAgreeDetail(billId, userId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "동의 서명", notes = "안건에 대해 동의 및 동의 취소")
    @PutMapping("/bill/agree/{billId}")
    public ResponseEntity<CommonResponse> setBillAgree(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill agree successfully", agreeService.setBillAgree(billId, param)), HttpStatus.OK);
    }
    
    
}