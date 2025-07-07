package kr.co.bestiansoft.ebillservicekg.eas.workRequest.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.WorkRequestService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
public class WorkRequestController {

    final WorkRequestService workRequestService;
    final DocumentWorkFlowService workFlowService;

    @ApiOperation(value="insertWorkRequest", notes = "insertWorkRequest")
    @PostMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> insertWorkRequest(@RequestBody WorkRequestVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.insertWorkRequest(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "to RcvId getWortRequest", notes = "to RcvId getWortRequest")
    @GetMapping("/eas/workRequest/{rcvId}")
    public ResponseEntity<CommonResponse> toRcvIdGetWorkRequestList(@PathVariable Integer rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestList(rcvId)), HttpStatus.OK);
    }
    @ApiOperation(value = "to docId getWortRequest", notes = "to docId getWortRequest")
    @GetMapping("/eas/workRequest/document/{docId}")
    public ResponseEntity<CommonResponse> toDocIdGetWorkRequestList(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestList(docId)), HttpStatus.OK);
    }
    @ApiOperation(value = "to docId getWortRequest", notes = "to docId getWortRequest")
    @GetMapping("/eas/workRequest/user/{docId}")
    public ResponseEntity<CommonResponse> workListAndResponse(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestAndResponseList(docId)), HttpStatus.OK);
    }

    @ApiOperation(value="delete work request", notes= "delete work request")
    @DeleteMapping("/eas/workRequest/{reqId}")
    public ResponseEntity<CommonResponse> deleteWorkRequest(@PathVariable int reqId) {
        workRequestService.deleteWorkRequest(reqId);
        return new ResponseEntity<>(new CommonResponse(200, "OK" ), HttpStatus.OK);
    }

    @ApiOperation(value = "updateWorkRequest", notes = "Update WorkRequest")
    @PutMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> updateWorkRequest(@RequestBody WorkRequestVo vo) {
        System.out.println("Updating workRequest: " + vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.updateWorkRequest(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="updateWorkRequestAndResponse", notes="update Work Request and Response")
    @PutMapping("/eas/workRequest/response")
    public ResponseEntity<CommonResponse> updateWorkRequestAndResponse (@RequestBody WorkRequestAndResponseVo vo){
        workFlowService.updateWorkRequest(vo);
        return  new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }

    @ApiOperation(value="insertWorkRequest and response", notes="insert work Request and response")
    @PostMapping("/eas/workRequest/response")
    public ResponseEntity<CommonResponse> insertWorkRequestAndResponse(@RequestBody WorkRequestAndResponseVo vo){
        workFlowService.insertWorkRequest(vo);
        return  new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }
}