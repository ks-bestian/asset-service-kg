package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.service.BillMngService;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "Agenda management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BillMngController {

    private final BillMngService billMngService;

    @Operation(summary = "Agenda management List check", description = "List Inquiry.")
    @GetMapping("/bill/review/billMng")
    public ResponseEntity<CommonResponse> getBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillList(param)), HttpStatus.OK);
    }
    @Operation(summary = "Agenda Details", description = "Details Inquiry.")
    @GetMapping("/bill/review/billMng/selectOneBill")
    public ResponseEntity<CommonResponse> selectOneBill(BillMngVo param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectOneBill(param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda entire particular check", description = "Details Inquiry.")
    @GetMapping("/bill/review/billMng/detail")
    public ResponseEntity<CommonResponse> getBillById(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.getBillById(param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda management - Agenda", description = "Agenda")
    @PostMapping(value = "/bill/review/billRegisterMng")
    public ResponseEntity<CommonResponse> billRegisterMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Agenda management - Committee", description = "Committee")
    @PostMapping(value = "/bill/review/billCmtRegMng")
    public ResponseEntity<CommonResponse> billCmtRegMng(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.billRegisterMng(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Agenda management Other information List inquiry", description = "Agenda management Other information check")
    @GetMapping("/bill/review/billBillEtcInfo")
    public ResponseEntity<CommonResponse> selectListBillEtcInfo(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListBillEtcInfo(param)), HttpStatus.OK);
    }

//    @Operation(summary = "Agenda management Legal review Details", description = "Agenda management Legal review Details")
//    @GetMapping("/bill/review/billLegalReview/{billId}")
//    public ResponseEntity<CommonResponse> selectOnelegalReview(@PathVariable String billId,@RequestParam HashMap<String, Object> param) {
//    	param.put("billId",billId );
//        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectOnelegalReview(param)), HttpStatus.OK);
//    }

    @Operation(summary = "Agenda Legal review registration", description = "Other information registration")
    @PostMapping(value = "/bill/review/billLegalReview")
    public ResponseEntity<CommonResponse> insertBillDetail(@RequestBody BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillDetail(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Agenda registration", description = "Agenda registration")
    @PostMapping(value = "/bill/review/billMng/detail", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertBillDetail2(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.insertBillDetail(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "promulgation registration", description = "promulgation registration")
    @PostMapping(value = "/bill/review/billMng/prmg", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertBillPrmg(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.insertBillPrmg(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "president refusal", description = "president refusal")
    @PostMapping(value = "/bill/review/billMng/presidentReject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> presidentReject(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "review created successfully", billMngService.presidentReject(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Agenda Legal review Review", description = "Legal review Review registration")
    @PostMapping(value = "/bill/review/billLegalReview/report")
    public ResponseEntity<CommonResponse> insertBillLegalReviewReport(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.insertBillLegalReviewReport(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "View agenda, competent committee, related committee, and review report.", description = "View agenda, competent committee, related committee, and review report.")
    @GetMapping("/bill/review/selectListCmtReviewReport")
    public ResponseEntity<CommonResponse> selectListCmtReviewReport(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListCmtReviewReport(param)), HttpStatus.OK);
    }

    @Operation(summary = "Bill meeting review", description = "Bill meeting review")
    @PostMapping(value = "/bill/review/cmtMeetingRvReport", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> insertCmtMeetingRvReport(BillMngVo billMngVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill meeting review create successfully", billMngService.insertCmtMeetingRvReport(billMngVo)), HttpStatus.CREATED);
    }


    @Operation(summary = "Agenda Review report delete", description = "Review report delete")
    @PutMapping(value = "/bill/review/deleteCmtReview")
    public ResponseEntity<CommonResponse> deleteCmtReview(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill Cmt review delete successfully", billMngService.deleteCmtReview(billMngVo)), HttpStatus.CREATED);
    }



    @Operation(summary = "List of agendas to be submitted to the plenary session.", description = "List of agendas to be submitted to the plenary session.")
    @GetMapping("/bill/review/selectListMainMtSubmit")
    public ResponseEntity<CommonResponse> selectListMainMtSubmit(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectListMainMtSubmit(param)), HttpStatus.OK);
    }



    ////////////////////////////////////////////////////////////////////////












    @Operation(summary = "Agenda management - Agenda registration", description = "Written receipt")
    @PostMapping(value = "/bill/review/billMng")
    public ResponseEntity<CommonResponse> createBill(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bill created successfully", billMngService.createBill(billMngVo)), HttpStatus.CREATED);
    }




    ///////

    @Operation(summary = "Co -author", description = "Agenda Agreed Council members Inquiry")
    @GetMapping("/bill/review/billMng/proposer")
    public ResponseEntity<CommonResponse> selectProposerByBillId(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billMngService.selectProposerByBillId(param)), HttpStatus.OK);
    }

    @Operation(summary = "committee Reverence", description = "Committee Book.")
    @PostMapping("/bill/review/billMng/committ")
    public ResponseEntity<CommonResponse> insertBillCommitt(@RequestBody BillMngVo billMngVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "bill committ created successfully", billMngService.insertBillCommitt(billMngVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "File attachment", description = "In the agenda review relevant File Attach")
    @PostMapping(value = "/bill/review/billMng/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CommonResponse> insertBillMngFile(BillMngVo billMngVo) throws Exception{
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", billMngService.insertBillDetailFile(billMngVo)), HttpStatus.CREATED);
	}

    @Operation(summary = "File deletion", description = "File Delete")
    @PutMapping(value = "/bill/review/billMng/delete/file")
    public ResponseEntity<CommonResponse> deleteEbsFile(@RequestBody EbsFileVo ebsFileVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "ebs file updated successfully", billMngService.updateEbsFileDelYn(ebsFileVo)), HttpStatus.OK);
    }


}