package kr.co.bestiansoft.ebillservicekg.admin.billMng.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.service.SystemBillService;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "Agenda management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class SystemBillController {

    private final SystemBillService adminBillMngService;

    @ApiOperation(value = "Agenda entire particular check", notes = "Details Inquiry.")
    @GetMapping("/system/bill/detail/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillDetail(billId, param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Legal review result", notes = "Legal review The result Enter.")
    @PostMapping("/system/bill/detail")
    public ResponseEntity<CommonResponse> createBillDetail(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "billDetail created successfully", adminBillMngService.createBillDetail(systemBillVo)), HttpStatus.CREATED); 
    }
    
    @ApiOperation(value = "Opinion file", notes = "Opinion File Inquiry")
    @GetMapping("/system/bill/opinion/{billId}")
    public ResponseEntity<CommonResponse> selectOpinionFile(@PathVariable String billId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectOpinionFile(billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Agenda", notes = "Agenda Create")
    @PostMapping(value = "/system/bill/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApply(SystemBillVo systemBillVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createBillFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "legal affairs department", notes = "안건 법적 행위 부서 상세를 입력 및 수정한다")
    @PostMapping(value = "/system/bill/legal", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateBillLegal(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.updateBillLegal(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Agenda entire particular check", notes = "Details Inquiry.")
    @GetMapping("/system/bill/mtng/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtng(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtng(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Insert competent committee information", notes = "Enter the competent committee information.")
    @PostMapping(value = "/system/mtng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtngFile(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtngFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Relevant verification department input", notes = "Relevant verification Department Enter")
    @PostMapping(value = "/system/bill/validation/dept", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createValidationDept(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createValidationDept(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "file note change", notes = "file Remarks Change")
    @PutMapping(value = "/system/bill/rmk/update")
    public ResponseEntity<CommonResponse> updateFileRmk(@RequestBody SystemBillVo systemBillVo) {
      return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "file Rmk updated successfully", adminBillMngService.updateFileRmk(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Relevant committee addition", notes = "Related Committee Add")
    @PostMapping(value = "/system/cmt/create")
    public ResponseEntity<CommonResponse> createMasterCmt(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMasterCmt(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Agenda entire particular check", notes = "Details Inquiry.")
    @GetMapping("/system/bill/mtn/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtnList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtnList(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Relevant verification department input", notes = "Relevant verification Department Enter")
    @PostMapping(value = "/system/bill/mnt/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtnMaster(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtnMaster(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Relevant committee particular check", notes = "Details Inquiry.")
    @GetMapping("/system/bill/recmt/{billId}")
    public ResponseEntity<CommonResponse> selectBillRelationMtngList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillRelationMtngList(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Relevant committee result", notes = "Relevant committee The result Enter")
    @PostMapping(value = "/system/relate/mtng", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeRelateMtng(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeRelateMtng(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "government result check", notes = "government The result Inquiry.")
    @GetMapping("/system/bill/goverment/{billId}")
    public ResponseEntity<CommonResponse> selectBillGoverment(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillGoverment(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Relevant committee result", notes = "Relevant committee The result Enter")
    @PostMapping(value = "/system/bill/goverment/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeGoverment(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeGoverment(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Agenda particular check", notes = "Agenda Details Inquiry")
    @GetMapping("/system/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> getBillDetail(@PathVariable String billId, @RequestParam String lang) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.getApplyDetail(billId, lang)), HttpStatus.OK);
    }
}