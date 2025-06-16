package kr.co.bestiansoft.ebillservicekg.eas.workResponse.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.WorkResponseService;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WorkResponseController {

    private final WorkResponseService workResponseService;

    @ApiOperation(value="insertWorkResponse", notes = "insertWorkResponse")
    @PostMapping("/eas/workResponse")
    public ResponseEntity<CommonResponse> insertWorkResponse(@RequestBody WorkResponseVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", workResponseService.insertWorkResponse(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="to rcvId get WorkResponse ", notes = "to rcvId get WorkResponse")
    @GetMapping("/eas/workResponse/{rcvId}")
    public ResponseEntity<CommonResponse> toRcvIdGetWorkResponse(@PathVariable int rcvId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", workResponseService.getWorkResponse(rcvId)), HttpStatus.OK);
    }

    @ApiOperation(value=" to docId get WorkResponse", notes = "to docId get WorkResponse")
    @GetMapping("/eas/workResponse/document/{docId}")
    public ResponseEntity<CommonResponse> toDocIdGetWorkResponse(@PathVariable String docId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", workResponseService.getWorkResponse(docId)), HttpStatus.OK);
    }
}