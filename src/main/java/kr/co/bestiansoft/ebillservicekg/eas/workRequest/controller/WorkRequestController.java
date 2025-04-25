package kr.co.bestiansoft.ebillservicekg.eas.workRequest.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.WorkRequestService;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
public class WorkRequestController {

    final WorkRequestService workRequestService;

    @ApiOperation(value="insertWorkRequest", notes = "insertWorkRequest")
    @PostMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> insertWorkRequest(@RequestBody WorkRequestVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.insertWorkRequest(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "getWortRequest", notes = "getWortRequest")
    @GetMapping("/eas/workRequest")
    public ResponseEntity<CommonResponse> getWorkRequestList(String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", workRequestService.getWorkRequestList(docId)), HttpStatus.OK);
    }
}