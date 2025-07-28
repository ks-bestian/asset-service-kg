package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.service.RevokeService;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo.RevokeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Agendant API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class RevokeController {

    private final RevokeService revokeService;
    private final ApplyService applyService;

    @Operation(summary = "Withdrawal list", description = "The withdrawal list Inquiry")
    @GetMapping("/bill/revoke")
    public ResponseEntity<CommonResponse> getRevokeList(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeService.getRevokeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal details", description = "Withdrawal details Inquiry")
    @GetMapping("/bill/revoke/{billId}")
    public ResponseEntity<CommonResponse> getRevokeDetail(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", revokeService.getRevokeDetail(billId, lang)), HttpStatus.OK);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", applyService.getApplyDetail(billId, param)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal request", description = "Withdrawal Request")
    @PostMapping(value = "/bill/revoke/request/{billId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> billRevokeRequest(@PathVariable String billId, RevokeVo vo) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke request successfully", revokeService.billRevokeRequest(billId,vo)), HttpStatus.OK);
    }

//    @Operation(summary = "proponent everyone Withdrawal Confirmation", description = "proponent All Withdrawal I agreed Check")
//    @PostMapping(value = "/bill/revoke/checkagree/{billId}")
//    public ResponseEntity<CommonResponse> billRevokeCheckAgree(@PathVariable String billId, RevokeVo vo) {
//    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke submit successfully", revokeService.hasEveryProposerAgreedToRevoke(billId)), HttpStatus.OK);
//    }

    @Operation(summary = "Request for withdrawal reception", description = "Withdrawal Request")
    @PostMapping(value = "/bill/revoke/submit/{billId}")
    public ResponseEntity<CommonResponse> billRevokeSubmit(@PathVariable String billId, RevokeVo vo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke submit successfully", revokeService.billRevokeSubmit(billId,vo)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal", description = "Request for withdrawal Cancel")
    @PutMapping("/bill/revoke/cancel/{billId}")
    public ResponseEntity<CommonResponse> billRevokeCancel(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke cancel successfully", revokeService.billRevokeCancel(billId, param)), HttpStatus.OK);
    }

    @Operation(summary = "Withdrawal", description = "Withdrawal Information Modify")
    @PutMapping("/bill/revoke/{billId}")
    public ResponseEntity<CommonResponse> updateBillRevoke(@PathVariable String billId, @RequestBody RevokeVo revokeVo) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "bill revoke update successfully", revokeService.updateRevoke(billId, revokeVo)), HttpStatus.OK);
    }

}