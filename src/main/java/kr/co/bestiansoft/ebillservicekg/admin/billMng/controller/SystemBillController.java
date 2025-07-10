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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.service.SystemBillService;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "Agenda management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class SystemBillController {

    private final SystemBillService adminBillMngService;

    @Operation(summary = "Agenda entire particular check", description = "Details Inquiry.")
    @GetMapping("/system/bill/detail/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillDetail(billId, param)), HttpStatus.OK);
    }

    @Operation(summary = "Legal review result", description = "Legal review The result Enter.")
    @PostMapping("/system/bill/detail")
    public ResponseEntity<CommonResponse> createBillDetail(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "billDetail created successfully", adminBillMngService.createBillDetail(systemBillVo)), HttpStatus.CREATED); 
    }
    
    @Operation(summary = "Opinion file", description = "Opinion File Inquiry")
    @GetMapping("/system/bill/opinion/{billId}")
    public ResponseEntity<CommonResponse> selectOpinionFile(@PathVariable String billId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectOpinionFile(billId)), HttpStatus.OK);
    }
    
    @Operation(summary = "Agenda", description = "Agenda Create")
    @PostMapping(value = "/system/bill/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApply(SystemBillVo systemBillVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createBillFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "legal affairs department", description = "Enter and update details of the agenda's Legal Affairs Department.")
    @PostMapping(value = "/system/bill/legal", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateBillLegal(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.updateBillLegal(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Agenda entire particular check", description = "Details Inquiry.")
    @GetMapping("/system/bill/mtng/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtng(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtng(billId, param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Insert competent committee information", description = "Enter the competent committee information.")
    @PostMapping(value = "/system/mtng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtngFile(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtngFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Relevant verification department input", description = "Relevant verification Department Enter")
    @PostMapping(value = "/system/bill/validation/dept", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createValidationDept(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createValidationDept(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "file note change", description = "file Remarks Change")
    @PutMapping(value = "/system/bill/rmk/update")
    public ResponseEntity<CommonResponse> updateFileRmk(@RequestBody SystemBillVo systemBillVo) {
      return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "file Rmk updated successfully", adminBillMngService.updateFileRmk(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Relevant committee addition", description = "Related Committee Add")
    @PostMapping(value = "/system/cmt/create")
    public ResponseEntity<CommonResponse> createMasterCmt(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMasterCmt(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Agenda entire particular check", description = "Details Inquiry.")
    @GetMapping("/system/bill/mtn/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtnList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtnList(billId, param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Relevant verification department input", description = "Relevant verification Department Enter")
    @PostMapping(value = "/system/bill/mnt/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtnMaster(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtnMaster(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Relevant committee particular check", description = "Details Inquiry.")
    @GetMapping("/system/bill/recmt/{billId}")
    public ResponseEntity<CommonResponse> selectBillRelationMtngList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillRelationMtngList(billId, param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Relevant committee result", description = "Relevant committee The result Enter")
    @PostMapping(value = "/system/relate/mtng", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeRelateMtng(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeRelateMtng(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "government result check", description = "government The result Inquiry.")
    @GetMapping("/system/bill/goverment/{billId}")
    public ResponseEntity<CommonResponse> selectBillGoverment(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillGoverment(billId, param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Relevant committee result", description = "Relevant committee The result Enter")
    @PostMapping(value = "/system/bill/goverment/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeGoverment(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeGoverment(systemBillVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Agenda particular check", description = "Agenda Details Inquiry")
    @GetMapping("/system/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> getBillDetail(@PathVariable String billId, @RequestParam String lang) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.getApplyDetail(billId, lang)), HttpStatus.OK);
    }
}