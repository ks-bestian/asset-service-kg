package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@Controller
@RequiredArgsConstructor
public class ReceivedInfoController {

    private final ReceivedInfoService infoService;
    private final DocumentWorkFlowService documentWorkFlowService;

    @ApiOperation(value="insertReceivedInfo", notes = "insertReceivedInfo")
    @PostMapping("/eas/receivedInfo")
    public ResponseEntity<CommonResponse> insertReceivedInfo(@RequestBody ReceivedInfoVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.insertReceivedInfo(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="updateReceivedInfo", notes = "updateReceivedInfo")
    @PutMapping("/eas/receivedInfo")
    public ResponseEntity<CommonResponse> insertReceivedInfo(@RequestBody UpdateReceivedInfoVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.updateReceivedInfo(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="getReceivedInfo", notes = "getReceivedInfo")
    @GetMapping("/eas/receivedInfo/{docId}")
    public ResponseEntity<CommonResponse> getReceivedInfo(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.getReceivedInfo(docId)), HttpStatus.OK);
    }
    @ApiOperation(value="get is receipt", notes = "get is receipt")
    @GetMapping("/eas/isReceipt/{rcvId}")
    public ResponseEntity<CommonResponse> getIsReceipt(@PathVariable int rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.isReceipt(rcvId)), HttpStatus.OK);
    }
    @ApiOperation(value = "reception" , notes = "reception")
    @PutMapping("/eas/reception")
    public ResponseEntity<CommonResponse> reception(@RequestBody WorkRequestAndResponseVo vo) {
        documentWorkFlowService.reception(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getDocId() ),HttpStatus.OK);
    }
    @ApiOperation(value = "get Main worker", notes = "get Main Worker")
    @GetMapping("/eas/receivedInfo/worker/{rcvId}")
    public ResponseEntity<CommonResponse> getMainWorker(@PathVariable int rcvId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK",infoService.getMainWorkerInfo(rcvId)  ),HttpStatus.OK);
    }
    @ApiOperation(value="reject", notes = "reject")
    @PutMapping("/eas/reject")
    public ResponseEntity<CommonResponse> reject (@RequestBody UpdateReceivedInfoVo vo) {
        documentWorkFlowService.rejectReception(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getRcvId()), HttpStatus.OK);
    }
    @ApiOperation(value="get ReceivedInfo by userId", notes = "get ReceivedInfo by userId")
    @GetMapping("/eas/receivedInfo/user/{docId}")
    public ResponseEntity<CommonResponse> getReceivedInfoByUserId(@PathVariable String docId){
        return  new ResponseEntity<>(new CommonResponse(200, "OK",infoService.getReceivedInfoByUserId(docId)), HttpStatus.OK);
    }
}