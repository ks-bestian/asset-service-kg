package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.service.RevokeAgreeService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Agendant API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class RevokeAgreeController {

    private final RevokeAgreeService revokeAgreeService;
    private final ApplyService applyService;

    @Operation(summary = "Withdrawal list", description = "The withdrawal list Inquiry")
    @GetMapping("/bill/revokeAgree")
    public ResponseEntity<CommonResponse> getRevokeList(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeAgreeService.getRevokeAgreeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal particular", description = "Withdrawal Details Inquiry")
    @GetMapping("/bill/revokeAgree/{billId}")
    public ResponseEntity<CommonResponse> getRevokeDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeAgreeService.getRevokeDetail(billId, param)), HttpStatus.OK);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal agreement", description = "Consent to withdrawal request and cancellation of consent")
    @PutMapping("/bill/revokeAgree/{billId}")
    public ResponseEntity<CommonResponse> updateRevokeAgree(@PathVariable String billId, @RequestBody HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "revoke agree successfully", revokeAgreeService.updateRevokeAgree(billId, param)), HttpStatus.OK);
    }

}