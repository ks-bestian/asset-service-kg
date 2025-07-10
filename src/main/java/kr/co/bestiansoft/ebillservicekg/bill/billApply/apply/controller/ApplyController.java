package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.controller;

import java.util.HashMap;

import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "agenda submission API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;
    private final ComFileService comFileService;

    @Operation(summary = "Agenda", description = "Agenda Create")
    @PostMapping(value = "/bill/apply", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApply(ApplyVo applyVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", applyService.createApply(applyVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Agenda registration", description = "Agenda Register")
    @PostMapping(value = "/bill/apply/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBillApplySubmit(ApplyVo applyVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "apply create successfully", applyService.createApplyRegister(applyVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "inventory check", description = "Agenda List Inquiry")
    @GetMapping("/bill/apply")
    public ResponseEntity<CommonResponse> getApplyList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda correction", description = "Agenda Modify")
    @PostMapping(value = "/bill/apply/update/{billId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateBillUpdate(@ModelAttribute ApplyVo applyVo, @PathVariable String billId) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply updated successfully", applyService.updateApply(applyVo, billId)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda delete", description = "Agenda Delete")
    @DeleteMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> deleteApply(@PathVariable String billId) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "apply delete successfully", applyService.deleteApply(billId)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda particular check", description = "Agenda Details Inquiry")
    @GetMapping("/bill/apply/{billId}")
    public ResponseEntity<CommonResponse> getBillDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }

//    @Operation(summary = "Agenda", description = "Agenda Receive.")
//    @PutMapping("/bill/apply/update/{billId}")
//    public ResponseEntity<CommonResponse> applyBill(@PathVariable String billId) {
//    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.applyBill(billId)), HttpStatus.OK);
//    }

    @Operation(summary = "Withdrawal", description = "Agenda Withdraw.")
    @PutMapping("/bill/apply/revoke/{billId}")
    public ResponseEntity<CommonResponse> revokeBill(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill apply successfully", applyService.revokeBill(billId, applyVo)), HttpStatus.OK);
    }

//    @Operation(summary = "State value change", description = "Agenda Status value Change")
//    @PutMapping("/bill/apply/status/{billId}")
//    public ResponseEntity<CommonResponse> updateBillState(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
//    	return new  ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill status update successfully", applyService.updateBillStatus(billId, applyVo)), HttpStatus.OK);
//    }

    @Operation(summary = "Agenda receipt", description = "Agenda receipt")
    @PostMapping("/bill/apply/accept/{billId}")
    public ResponseEntity<CommonResponse> saveBillAccept(@PathVariable String billId, @RequestBody ApplyVo applyVo) {
    	return new  ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill status update successfully", applyService.saveBillAccept(billId, applyVo)), HttpStatus.OK);
    }

    @Operation(summary = "file delete", description = "Agenda In modification File Delete")
    @PutMapping("/bill/file/delete")
    public ResponseEntity<CommonResponse> deleteBillFile(@RequestBody EbsFileVo ebsFileVo){
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill file delete successfully", applyService.deleteBillFile(ebsFileVo)), HttpStatus.OK);
    }
    
    @Operation(summary = "Modify whether the file is disclosed", description = "Modify whether the file is disclosed")
    @PutMapping("/bill/file/updateopen")
    public ResponseEntity<CommonResponse> updateopenBillFile(@RequestBody EbsFileVo ebsFileVo){
    	applyService.updateFileOpbYn(ebsFileVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "updated successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Agenda entire check", description = "The entire agenda Inquiry")
    @GetMapping("/bill/all")
    public ResponseEntity<CommonResponse> selectBillAll(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.selectBillAll(param)), HttpStatus.OK);
    }

    @Operation(summary = "Homepage Publication", description = "Agenda On the homepage Post")
    @PostMapping(value = "/bill/apply/home")
    public ResponseEntity<CommonResponse> createBillHome(@RequestBody ApplyVo applyVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "insert homepage successfully", applyService.createBillHome(applyVo)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Homepage Publication stop", description = "Agenda On the homepage Publication Stop")
    @PostMapping(value = "/bill/apply/home/stop")
    public ResponseEntity<CommonResponse> stopBillHome(@RequestBody ApplyVo applyVo) {
    	applyService.stopBillHome(applyVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "stopped successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Homepage Reply registration", description = "Homepage Reply registration")
    @PostMapping(value = "law/comment")
    public ResponseEntity<CommonResponse> createComment(@RequestBody CommentsVo commentsVo) {
        applyService.createComments(commentsVo);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "successfully"), HttpStatus.OK);
    };

}