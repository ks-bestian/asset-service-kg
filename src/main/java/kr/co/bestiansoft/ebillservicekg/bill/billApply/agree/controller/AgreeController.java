package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.service.AgreeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "agenda approval API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AgreeController {

    private final AgreeService agreeService;
    private final ApplyService applyService;

    @ApiOperation(value = "Signature inventory", notes = "Signature List check")
    @GetMapping("/bill/agree")
    public ResponseEntity<CommonResponse> getApplyList(@RequestParam HashMap<String, Object> param) {
		// TODO :: 대수 검색조건 설정 필요(현재 14로 하드코딩)
        return new ResponseEntity<>(new CommonResponse(200, "OK", agreeService.getAgreeList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Signature particular", notes = "Signature Details check")
    @GetMapping("/bill/agree/{billId}")
    public ResponseEntity<CommonResponse> getApplyDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	//todo :: log in posthumous work id To bring change necessary
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", agreeService.getAgreeDetail(billId, lang)), HttpStatus.OK);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "agreement signature", notes = "Agenda about agreement and agreement cancellation")
    @PutMapping("/bill/agree/{billId}")
    public ResponseEntity<CommonResponse> setBillAgree(@PathVariable String billId, @RequestBody HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill agree successfully", agreeService.setBillAgree(billId, param)), HttpStatus.OK);
    }
    
    
}