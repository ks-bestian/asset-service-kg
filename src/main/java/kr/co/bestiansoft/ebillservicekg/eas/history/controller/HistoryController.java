package kr.co.bestiansoft.ebillservicekg.eas.history.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@Controller
public class HistoryController {
    final HistoryService historyService;

    @ApiOperation(value="insertHistory", notes = "insertHistory")
    @PostMapping("/eas/history")
    public ResponseEntity<CommonResponse> insertHistory(@RequestBody HistoryVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", historyService.insertHistory(vo)), HttpStatus.OK);
    }

}