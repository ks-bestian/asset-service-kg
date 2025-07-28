package kr.co.bestiansoft.ebillservicekg.eas.workRequest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.WorkRequestService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WorkRequestController {

    final WorkRequestService workRequestService;
    final DocumentWorkFlowService workFlowService;

    @Operation(summary = "insertWorkRequest", description = "insertWorkRequest")
    @PostMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> insertWorkRequest(@RequestBody WorkRequestVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.insertWorkRequest(vo)), HttpStatus.OK);
    }
    @Operation(summary = "to RcvId getWortRequest", description = "to RcvId getWortRequest")
    @GetMapping("/eas/workRequest/{rcvId}")
    public ResponseEntity<CommonResponse> toRcvIdGetWorkRequestList(@PathVariable Integer rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestList(rcvId)), HttpStatus.OK);
    }
    @Operation(summary = "to docId getWortRequest", description = "to docId getWortRequest")
    @GetMapping("/eas/workRequest/document/{docId}")
    public ResponseEntity<CommonResponse> toDocIdGetWorkRequestList(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestList(docId)), HttpStatus.OK);
    }
    @Operation(summary = "to docId getWortRequest", description = "to docId getWortRequest")
    @GetMapping("/eas/workRequest/user/{docId}")
    public ResponseEntity<CommonResponse> workListAndResponse(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestAndResponseList(docId)), HttpStatus.OK);
    }

    @Operation(summary = "delete work request", description= "delete work request")
    @DeleteMapping("/eas/workRequest/{reqId}")
    public ResponseEntity<CommonResponse> deleteWorkRequest(@PathVariable int reqId) {
        workFlowService.deleteWorkRequest(reqId);
        return new ResponseEntity<>(new CommonResponse(200, "OK" ), HttpStatus.OK);
    }

    @Operation(summary = "updateWorkRequest", description = "Update WorkRequest")
    @PutMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> updateWorkRequest(@RequestBody WorkRequestVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.updateWorkRequest(vo)), HttpStatus.OK);
    }
    @Operation(summary = "updateWorkRequestAndResponse", description="update Work Request and Response")
    @PutMapping("/eas/workRequest/response")
    public ResponseEntity<CommonResponse> updateWorkRequestAndResponse (@RequestBody WorkRequestAndResponseVo vo){
        workFlowService.updateWorkRequest(vo);
        return  new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }

    @Operation(summary = "insertWorkRequest and response", description="insert work Request and response")
    @PostMapping("/eas/workRequest/response")
    public ResponseEntity<CommonResponse> insertWorkRequestAndResponse(@RequestBody WorkRequestAndResponseVo vo){
        workFlowService.insertWorkRequest(vo);
        return  new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }
}