package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;

@Controller
@RequiredArgsConstructor
public class ReceivedInfoController {

    final ReceivedInfoService infoService;

    @ApiOperation(value="insertReceivedInfo", notes = "insertReceivedInfo")
    @PostMapping("/eas/receivedInfo")
    public ResponseEntity<CommonResponse> insertReceivedInfo(@RequestBody ReceivedInfoVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", infoService.insertReceivedInfo(vo)), HttpStatus.OK);
    }
}