package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReceivedInfoController {

    private final ReceivedInfoService infoService;
    private final DocumentWorkFlowService documentWorkFlowService;

    @Operation(summary = "insertReceivedInfo", description = "insertReceivedInfo")
    @PostMapping("/eas/receivedInfo")
    public ResponseEntity<CommonResponse> insertReceivedInfo(@RequestBody ReceivedInfoVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.insertReceivedInfo(vo)), HttpStatus.OK);
    }
    @Operation(summary = "updateReceivedInfo", description = "updateReceivedInfo")
    @PutMapping("/eas/receivedInfo")
    public ResponseEntity<CommonResponse> insertReceivedInfo(@RequestBody UpdateReceivedInfoVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.updateReceivedInfo(vo)), HttpStatus.OK);
    }
    @Operation(summary = "getReceivedInfo", description = "getReceivedInfo")
    @GetMapping("/eas/receivedInfo/{docId}")
    public ResponseEntity<CommonResponse> getReceivedInfo(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.getReceivedInfo(docId)), HttpStatus.OK);
    }
    @Operation(summary = "get is receipt", description = "get is receipt")
    @GetMapping("/eas/isReceipt/{rcvId}")
    public ResponseEntity<CommonResponse> getIsReceipt(@PathVariable int rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.isReceipt(rcvId)), HttpStatus.OK);
    }
    @Operation(summary = "reception" , description = "reception")
    @PutMapping("/eas/reception")
    public ResponseEntity<CommonResponse> reception(@RequestBody WorkRequestAndResponseVo vo) {
        documentWorkFlowService.reception(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getDocId() ),HttpStatus.OK);
    }
    @Operation(summary = "get Main worker", description = "get Main Worker")
    @GetMapping("/eas/receivedInfo/worker/{rcvId}")
    public ResponseEntity<CommonResponse> getMainWorker(@PathVariable int rcvId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK",infoService.getMainWorkerInfo(rcvId)  ),HttpStatus.OK);
    }
    @Operation(summary = "reject", description = "reject")
    @PutMapping("/eas/reject")
    public ResponseEntity<CommonResponse> reject (@RequestBody UpdateReceivedInfoVo vo) {
        documentWorkFlowService.rejectReception(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getRcvId()), HttpStatus.OK);
    }
    @Operation(summary = "get ReceivedInfo by userId", description = "get ReceivedInfo by userId")
    @GetMapping("/eas/receivedInfo/user/{docId}")
    public ResponseEntity<CommonResponse> getReceivedInfoByUserId(@PathVariable String docId){
        return  new ResponseEntity<>(new CommonResponse(200, "OK",infoService.getReceivedInfoByUserId(docId)), HttpStatus.OK);
    }
    @Operation(summary = "update Main Worker", description= "update Main Worker")
    @PutMapping("/eas/receivedInfo/worker")
    public ResponseEntity<CommonResponse> updateMainWorker(@RequestBody UpdateReceivedInfoVo vo){
        documentWorkFlowService.updateMainResponser(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getRcvId()), HttpStatus.OK);
    }
}