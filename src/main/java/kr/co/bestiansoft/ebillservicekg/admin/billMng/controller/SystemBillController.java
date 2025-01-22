package kr.co.bestiansoft.ebillservicekg.admin.billMng.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping("/system/bill/mng/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", adminBillMngService.selectBillDetail(billId, lang)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률검토 결과", notes = "법률검토 결과를 입력한다.")
    @PostMapping("/system/bill/mng")
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
}