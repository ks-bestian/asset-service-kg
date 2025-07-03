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

@Api(tags = "Agenda management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BillMngController {

    private final BillMngService billMngService;

    @ApiOperation(value = "Agenda management List check", notes = "List Inquiry.")
    @GetMapping("/bill/review/billMng")
    public ResponseEntity<CommonResponse> getBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillList(param)), HttpStatus.OK);
    }
    @ApiOperation(value = "Agenda Details", notes = "Details Inquiry.")
    @GetMapping("/bill/review/billMng/selectOneBill")
    public ResponseEntity<CommonResponse> selectOneBill(BillMngVo param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectOneBill(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Agenda entire particular check", notes = "Details Inquiry.")
    @GetMapping("/bill/review/billMng/detail")
    public ResponseEntity<CommonResponse> getBillById(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillById(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Agenda management - Agenda", notes = "Agenda")
    @PostMapping(value = "/bill/review/billRegisterMng")
    public ResponseEntity<CommonResponse> billRegisterMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Agenda management - Committee", notes = "Committee")
    @PostMapping(value = "/bill/review/billCmtRegMng")
    public ResponseEntity<CommonResponse> billCmtRegMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Agenda management Other information List inquiry", notes = "Agenda management Other information check")
    @GetMapping("/bill/review/billBillEtcInfo")
    public ResponseEntity<CommonResponse> selectListBillEtcInfo(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListBillEtcInfo(param)), HttpStatus.OK);
    }

//    @ApiOperation(value = "Agenda management Legal review Details", notes = "Agenda management Legal review Details")
//    @GetMapping("/bill/review/billLegalReview/{billId}")
//    public ResponseEntity<CommonResponse> selectOnelegalReview(@PathVariable String billId,@RequestParam HashMap<String, Object> param) {
//    	param.put("billId",billId );
//        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectOnelegalReview(param)), HttpStatus.OK);
//    }

    @ApiOperation(value = "Agenda Legal review registration", notes = "Other information registration")
    @PostMapping(value = "/bill/review/billLegalReview")
    public ResponseEntity<CommonResponse> insertBillDetail(@RequestBody BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillDetail(billMngVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "Agenda registration", notes = "Agenda registration")
    @PostMapping(value = "/bill/review/billMng/detail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertBillDetail2(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.insertBillDetail(billMngVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "promulgation registration", notes = "promulgation registration")
    @PostMapping(value = "/bill/review/billMng/prmg", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertBillPrmg(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.insertBillPrmg(billMngVo)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "president refusal", notes = "president refusal")
    @PostMapping(value = "/bill/review/billMng/presidentReject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> presidentReject(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.presidentReject(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Agenda Legal review Review", notes = "Legal review Review registration")
    @PostMapping(value = "/bill/review/billLegalReview/report")
    public ResponseEntity<CommonResponse> insertBillLegalReviewReport(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillLegalReviewReport(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "View agenda, competent committee, related committee, and review report.", notes = "View agenda, competent committee, related committee, and review report.")
    @GetMapping("/bill/review/selectListCmtReviewReport")
    public ResponseEntity<CommonResponse> selectListCmtReviewReport(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListCmtReviewReport(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Bill meeting review", notes = "Bill meeting review")
    @PostMapping(value = "/bill/review/cmtMeetingRvReport", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertCmtMeetingRvReport(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill meeting review create successfully", billMngService.insertCmtMeetingRvReport(billMngVo)), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Agenda Review report delete", notes = "Review report delete")
    @PutMapping(value = "/bill/review/deleteCmtReview")
    public ResponseEntity<CommonResponse> deleteCmtReview(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill Cmt review delete successfully", billMngService.deleteCmtReview(billMngVo)), HttpStatus.CREATED);
    }



    @ApiOperation(value = "List of agendas to be submitted to the plenary session.", notes = "List of agendas to be submitted to the plenary session.")
    @GetMapping("/bill/review/selectListMainMtSubmit")
    public ResponseEntity<CommonResponse> selectListMainMtSubmit(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListMainMtSubmit(param)), HttpStatus.OK);
    }



    ////////////////////////////////////////////////////////////////////////












    @ApiOperation(value = "Agenda management - Agenda registration", notes = "Written receipt")
    @PostMapping(value = "/bill/review/billMng")
    public ResponseEntity<CommonResponse> createBill(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.createBill(billMngVo)), HttpStatus.CREATED);
    }




    ///////

    @ApiOperation(value = "Co -author", notes = "Agenda Agreed Council members Inquiry")
    @GetMapping("/bill/review/billMng/proposer")
    public ResponseEntity<CommonResponse> selectProposerByBillId(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectProposerByBillId(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "committee Reverence", notes = "Committee Book.")
    @PostMapping("/bill/review/billMng/committ")
    public ResponseEntity<CommonResponse> insertBillCommitt(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "bill committ created successfully", billMngService.insertBillCommitt(billMngVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "File attachment", notes = "In the agenda review relevant File Attach")
    @PostMapping(value = "/bill/review/billMng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CommonResponse> insertBillMngFile(BillMngVo billMngVo) throws Exception{
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", billMngService.insertBillDetailFile(billMngVo)), HttpStatus.CREATED);
	}

    @ApiOperation(value = "File deletion", notes = "File Delete")
    @PutMapping(value = "/bill/review/billMng/delete/file")
    public ResponseEntity<CommonResponse> deleteEbsFile(@RequestBody EbsFileVo ebsFileVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "ebs file updated successfully", billMngService.updateEbsFileDelYn(ebsFileVo)), HttpStatus.OK);
    }


}