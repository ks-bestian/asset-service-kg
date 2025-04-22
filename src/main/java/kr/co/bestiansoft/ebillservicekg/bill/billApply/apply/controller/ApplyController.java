package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건제출 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;

    @ApiOperation(value = "안건제출", notes = "안건을 생성한다")
    @PostMapping(value = "/bill/apply", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApply(ApplyVo applyVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", applyService.createApply(applyVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "안건접수 등록", notes = "안건접수 등록한다")
    @PostMapping(value = "/bill/apply/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApplySubmit(ApplyVo applyVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", applyService.createApplyRegister(applyVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "목록 조회", notes = "안건제출 목록을 조회한다")
    @GetMapping("/bill/apply")
    public ResponseEntity<CommonResponse> getApplyList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "안건 수정", notes = "안건을 수정한다")
    @PostMapping(value = "/bill/apply/update/{billId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateBillUpdate(@ModelAttribute ApplyVo applyVo, @PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply updated successfully", applyService.updateApply(applyVo, billId)), HttpStatus.OK);
    }

    @ApiOperation(value = "안건 삭제", notes = "안건을 삭제한다")
    @DeleteMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> deleteApply(@PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully", applyService.deleteApply(billId)), HttpStatus.OK);
    }

    @ApiOperation(value = "안건 상세 조회", notes = "안건의 상세를 조회한다")
    @GetMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> getBillDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }

//    @ApiOperation(value = "안건접수", notes = "안건을 접수한다.")
//    @PutMapping("/bill/apply/update/{billId}")
//    public ResponseEntity<CommonResponse> applyBill(@PathVariable String billId) {
//    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.applyBill(billId)), HttpStatus.OK);
//    }

    @ApiOperation(value = "안건철회", notes = "안건을 철회한다.")
    @PutMapping("/bill/apply/revoke/{billId}")
    public ResponseEntity<CommonResponse> revokeBill(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.revokeBill(billId, applyVo)), HttpStatus.OK);
    }

//    @ApiOperation(value = "상태값 변경", notes = "안건의 상태값을 변경한다")
//    @PutMapping("/bill/apply/status/{billId}")
//    public ResponseEntity<CommonResponse> updateBillState(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
//    	return new  ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill status update successfully", applyService.updateBillStatus(billId, applyVo)), HttpStatus.OK);
//    }

    @ApiOperation(value = "안건 접수", notes = "안건 접수")
    @PostMapping("/bill/apply/accept/{billId}")
    public ResponseEntity<CommonResponse> saveBillAccept(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new  ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill status update successfully", applyService.saveBillAccept(billId, applyVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "파일 삭제", notes = "안건 수정에서 파일을 삭제한다")
    @PutMapping("/bill/file/delete")
    public ResponseEntity<CommonResponse> deleteBillFile(@RequestBody EbsFileVo ebsFileVo){
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill file delete successfully", applyService.deleteBillFile(ebsFileVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "안건 전체 조회", notes = "안건전체를 조회한다")
    @GetMapping("/bill/all")
    public ResponseEntity<CommonResponse> selectBillAll(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.selectBillAll(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "홈페이지 게재", notes = "안건을 홈페이지에 게재한다")
    @PostMapping(value = "/bill/apply/home")
    public ResponseEntity<CommonResponse> createBillHome(@RequestBody ApplyVo applyVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "insert homepage successfully", applyService.createBillHome(applyVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "홈페이지 게재 중지", notes = "안건을 홈페이지에 게재 중지한다")
    @PostMapping(value = "/bill/apply/home/stop")
    public ResponseEntity<CommonResponse> stopBillHome(@RequestBody ApplyVo applyVo) {
    	applyService.stopBillHome(applyVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "stopped successfully"), HttpStatus.OK);
    }

}