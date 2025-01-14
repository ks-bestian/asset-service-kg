package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.controller;

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
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건동의 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class RevokeController {

    private final RevokeService revokeService;
    
    @ApiOperation(value = "철회목록", notes = "철회목록을 조회한다")
    @GetMapping("/bill/revoke")
    public ResponseEntity<CommonResponse> getRevokeList(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeService.getRevokeList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회상세", notes = "철회상세를 조회한다")
    @GetMapping("/bill/revoke/{billId}")
    public ResponseEntity<CommonResponse> getRevokeDetail(@PathVariable String billId, @RequestParam String lang) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeService.getRevokeDetail(billId, lang)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회접수요청", notes = "철회접수를 요청한다")
    @PutMapping("/bill/revoke/request/{billId}")
    public ResponseEntity<CommonResponse> billRevokeRequest(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke request successfully", revokeService.billRevokeRequest(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회취소", notes = "철회요청을 취소한다")
    @PutMapping("/bill/revoke/cancel/{billId}")
    public ResponseEntity<CommonResponse> billRevokeCancel(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke cancel successfully", revokeService.billRevokeCancle(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "철회수정", notes = "철회 정보를 수정한다")
    @PutMapping("/bill/revoke/{billId}")
    public ResponseEntity<CommonResponse> updateBillRevoke(@PathVariable String billId, @RequestBody RevokeVo revokeVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke update successfully", revokeService.updateRevoke(billId, revokeVo)), HttpStatus.OK);
    }
    
}