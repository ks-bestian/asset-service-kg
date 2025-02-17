package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건 관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BillMngController {

    private final BillMngService billMngService;

    @ApiOperation(value = "안건 관리 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/review/billMng")
    public ResponseEntity<CommonResponse> getBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "안건 관리 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/review/billMng/detail")
    public ResponseEntity<CommonResponse> getBillById(BillMngVo param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillById(param)), HttpStatus.OK);
    }




    @ApiOperation(value = "안건 관리 - 안건접수", notes = "안건접수")
    @PostMapping(value = "/bill/review/billRegisterMng")
    public ResponseEntity<CommonResponse> billRegisterMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "안건 관리 - 위원회회부", notes = "위원회회부")
    @PostMapping(value = "/bill/review/billCmtRegMng")
    public ResponseEntity<CommonResponse> billCmtRegMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "안건 관리 기타정보 목록조회", notes = "안건 관리 기타정보 조회")
    @GetMapping("/bill/review/billBillEtcInfo")
    public ResponseEntity<CommonResponse> selectListBillEtcInfo(BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListBillEtcInfo(billMngVo)), HttpStatus.OK);
    }

//    @ApiOperation(value = "안건 관리 법률검토 상세조회", notes = "안건 관리 법률검토 상세조회")
//    @GetMapping("/bill/review/billLegalReview/{billId}")
//    public ResponseEntity<CommonResponse> selectOnelegalReview(@PathVariable String billId,@RequestParam HashMap<String, Object> param) {
//    	param.put("billId",billId );
//        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectOnelegalReview(param)), HttpStatus.OK);
//    }

    @ApiOperation(value = "안건관리 법률검토 등록", notes = "기타정보 등록")
    @PostMapping(value = "/bill/review/billLegalReview")
    public ResponseEntity<CommonResponse> insertBillDetail(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillDetail(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "안건관리 법률검토 검토보고", notes = "법률검토 검토보고 등록")
    @PostMapping(value = "/bill/review/billLegalReview/report")
    public ResponseEntity<CommonResponse> insertBillLegalReviewReport(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillLegalReviewReport(billMngVo)), HttpStatus.CREATED);
    }


    @ApiOperation(value = "안건위원회 소관위,관련위 심사보고서  조회", notes = " 소관위,관련위 심사보고서 리스트를 조회한다.")
    @GetMapping("/bill/review/selectListCmtReviewReport")
    public ResponseEntity<CommonResponse> selectListCmtReviewReport(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListCmtReviewReport(param)), HttpStatus.OK);
    }









    ////////////////////////////////////////////////////////////////////////












    @ApiOperation(value = "안건 관리 - 안건 등록", notes = "서면 접수")
    @PostMapping(value = "/bill/review/billMng")
    public ResponseEntity<CommonResponse> createBill(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.createBill(billMngVo)), HttpStatus.CREATED);
    }




    ///////

    @ApiOperation(value = "공동발의자", notes = "안건에 동의한 의원들을 조회한다")
    @GetMapping("/bill/review/billMng/proposer")
    public ResponseEntity<CommonResponse> selectProposerByBillId(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectProposerByBillId(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "위원회 회부", notes = "위원회를 회부한다.")
    @PostMapping("/bill/review/billMng/committ")
    public ResponseEntity<CommonResponse> insertBillCommitt(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "bill committ created successfully", billMngService.insertBillCommitt(billMngVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "파일첨부", notes = "안건심사에 관련된 파일을 첨부한다")
    @PostMapping(value = "/bill/review/billMng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CommonResponse> insertBillMngFile(EbsFileVo ebsfileVo){
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", billMngService.insertBillMngFile(ebsfileVo)), HttpStatus.CREATED);
	}
    
    @ApiOperation(value = "파일삭제", notes = "파일을 삭제한다")
    @PutMapping(value = "/bill/review/billMng/delete/file")
    public ResponseEntity<CommonResponse> deleteEbsFile(@RequestBody EbsFileVo ebsFileVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "ebs file updated successfully", billMngService.updateEbsFileDelYn(ebsFileVo)), HttpStatus.OK);
    }
    

}