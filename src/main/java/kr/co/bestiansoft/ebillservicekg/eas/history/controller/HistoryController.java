    package kr.co.bestiansoft.ebillservicekg.eas.history.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

@RequiredArgsConstructor
@Controller
public class HistoryController {
    final HistoryService historyService;

    @Operation(summary = "insertHistory", description = "insertHistory")
    @PostMapping("/eas/history")
    public ResponseEntity<CommonResponse> insertHistory(@RequestBody HistoryVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", historyService.insertHistory(vo)), HttpStatus.OK);
    }
    @Operation(summary = "getHistory", description = "getHistory")
    @GetMapping("/eas/history/{docId}")
    public ResponseEntity<CommonResponse> getHistory(@PathVariable String docId){
        return  new ResponseEntity<>(new CommonResponse(200, "OK", historyService.getHistory(docId)), HttpStatus.OK);
    }
    @Operation(summary = "getHistoryByUserIdAndDocument" , description = "get history by userId and document id ")
    @GetMapping("/eas/history/user/{docId}")
    public ResponseEntity<CommonResponse> getHistoryByUserId(@PathVariable String docId){
        return  new ResponseEntity<>(new CommonResponse(200, "OK", historyService.getHistoryByUserId(docId)), HttpStatus.OK);
    }

}