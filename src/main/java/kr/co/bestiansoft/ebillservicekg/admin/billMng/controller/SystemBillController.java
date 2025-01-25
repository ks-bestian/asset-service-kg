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

@Api(tags = "안건 관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class SystemBillController {

    private final SystemBillService adminBillMngService;

    @ApiOperation(value = "안건 전체 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/system/bill/detail/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillDetail(billId, param)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률검토 결과", notes = "법률검토 결과를 입력한다.")
    @PostMapping("/system/bill/detail")
    public ResponseEntity<CommonResponse> createBillDetail(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "billDetail created successfully", adminBillMngService.createBillDetail(systemBillVo)), HttpStatus.CREATED); 
    }
    
    @ApiOperation(value = "의견서 파일", notes = "의견서 파일을 조회한다")
    @GetMapping("/system/bill/opinion/{billId}")
    public ResponseEntity<CommonResponse> selectOpinionFile(@PathVariable String billId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectOpinionFile(billId)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "안건제출", notes = "안건을 생성한다")
    @PostMapping(value = "/system/bill/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApply(SystemBillVo systemBillVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createBillFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "법적행위부서", notes = "안건 법적행위부서 상세를 입력 및 수정한다")
    @PostMapping(value = "/system/bill/legal", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateBillLegal(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.updateBillLegal(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "안건 전체 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/system/bill/mtng/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtng(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtng(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "소관위 입력", notes = "소관위 정보를 입력한다")
    @PostMapping(value = "/system/mtng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtngFile(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtngFile(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "관련검증 부서 입력", notes = "관련검증 부서를 입력한다")
    @PostMapping(value = "/system/bill/validation/dept", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createValidationDept(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createValidationDept(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "파일 비고 변경", notes = "파일 비고를 변경한다")
    @PutMapping(value = "/system/bill/rmk/update")
    public ResponseEntity<CommonResponse> updateFileRmk(@RequestBody SystemBillVo systemBillVo) {
      return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "file Rmk updated successfully", adminBillMngService.updateFileRmk(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "관련위원회 추가", notes = "관련위원회를 추가한다")
    @PostMapping(value = "/system/cmt/create")
    public ResponseEntity<CommonResponse> createMasterCmt(@RequestBody SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMasterCmt(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "안건 전체 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/system/bill/mtn/{billId}")
    public ResponseEntity<CommonResponse> selectBillMtnList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillMtnList(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "관련검증 부서 입력", notes = "관련검증 부서를 입력한다")
    @PostMapping(value = "/system/bill/mnt/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtnMaster(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.createMtnMaster(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "관련위원회 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/system/bill/recmt/{billId}")
    public ResponseEntity<CommonResponse> selectBillRelationMtngList(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillRelationMtngList(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "관련위원회 결과", notes = "관련위원회 결과를 입력한다")
    @PostMapping(value = "/system/relate/mtng", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeRelateMtng(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeRelateMtng(systemBillVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "정부 결과 조회", notes = "정부 결과를 조회한다.")
    @GetMapping("/system/bill/goverment/{billId}")
    public ResponseEntity<CommonResponse> selectBillGoverment(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillGoverment(billId, param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "관련위원회 결과", notes = "관련위원회 결과를 입력한다")
    @PostMapping(value = "/system/bill/goverment/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> cretaeGoverment(SystemBillVo systemBillVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "file create successfully", adminBillMngService.cretaeGoverment(systemBillVo)), HttpStatus.CREATED);
    }
}