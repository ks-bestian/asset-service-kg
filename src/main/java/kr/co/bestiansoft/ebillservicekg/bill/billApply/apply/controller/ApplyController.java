package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건제출 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;

    @ApiOperation(value = "안건제출", notes = "안건을 생성한다")
    @PostMapping("/bill/apply")
    public ResponseEntity<CommonResponse> createBillApply(@RequestBody ApplyVo applyVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", applyService.createApply(applyVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "목록 조회", notes = "안건제출 목록을 조회한다")
    @GetMapping("/bill/apply")
    public ResponseEntity<CommonResponse> getApplyList(@RequestParam HashMap<String, Object> param) {
		// TODO :: 대수 검색조건 설정 필요(현재 14로 하드코딩)
        return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건 수정", notes = "안건을 수정한다")
    @PutMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> updateBillUpdate(@RequestBody ApplyVo applyVo, @PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply updated successfully", applyService.updateApply(applyVo, billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건 삭제", notes = "안건을 삭제한다")
    @DeleteMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> deleteApply(@PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully", applyService.deleteApply(billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건 상세 조회", notes = "안건의 상세를 조회한다")
    @GetMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> getBillDetail(@PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건접수", notes = "안건을 접수한다.")
    @PutMapping("/bill/apply/update/{billId}")
    public ResponseEntity<CommonResponse> applyBill(@PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.applyBill(billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건철회", notes = "안건을 철회한다.")
    @PutMapping("/bill/apply/revoke/{billId}")
    public ResponseEntity<CommonResponse> revokeBill(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.revokeBill(billId, applyVo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "상태값 변경", notes = "안건의 상태값을 변경한다")
    @PutMapping("/bill/apply/status/{billId}")
    public ResponseEntity<CommonResponse> updateBillState(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new  ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill status update successfully", applyService.updateBillStatus(billId, applyVo)), HttpStatus.OK);
    }

}