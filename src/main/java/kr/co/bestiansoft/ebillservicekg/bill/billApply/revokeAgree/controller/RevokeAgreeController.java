package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.controller;

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
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.service.RevokeAgreeService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건동의 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class RevokeAgreeController {

    private final RevokeAgreeService revokeAgreeService;
    private final ApplyService applyService;
    
    @ApiOperation(value = "철회목록", notes = "철회목록을 조회한다")
    @GetMapping("/bill/revokeAgree")
    public ResponseEntity<CommonResponse> getRevokeList(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeAgreeService.getRevokeAgreeList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회 상세", notes = "철회 상세를 조회한다")
    @GetMapping("/bill/revokeAgree/{billId}")
    public ResponseEntity<CommonResponse> getRevokeDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeAgreeService.getRevokeDetail(billId, param)), HttpStatus.OK);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회 동의", notes = "철회 요청에 대한 동의 및 동의취소를 한다")
    @PutMapping("/bill/revokeAgree/{billId}")
    public ResponseEntity<CommonResponse> updateRevokeAgree(@PathVariable String billId, @RequestBody HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "revoke agree successfully", revokeAgreeService.updateRevokeAgree(billId, param)), HttpStatus.OK);
    }
    
}