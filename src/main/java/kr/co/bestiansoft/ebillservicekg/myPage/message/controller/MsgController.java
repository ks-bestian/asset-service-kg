package kr.co.bestiansoft.ebillservicekg.myPage.message.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.myPage.message.service.MsgService;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "메세지관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MsgController {

    private final MsgService msgService;
    @ApiOperation(value = "메세지 수신 리스트 조회", notes = "메세지 수신 리스트를 조회한다.")
    @GetMapping("myPage/msg/rcv")
    public ResponseEntity<CommonResponse> getMsgRcvList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getRcvList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 발신 리스트 조회", notes = "메세지 발신 리스트를 조회한다.")
    @GetMapping("/myPage/msg/send")
    public ResponseEntity<CommonResponse> getMsgSendList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getSendList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 상세 조회", notes = "메세지 상세를 조회한다.")
    @GetMapping("/myPage/msg/{msgId}")
    public ResponseEntity<CommonResponse> getMsgDetail(@PathVariable Long msgId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getMsgDetail(msgId)), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 전송", notes = "메세지를 전송한다.")
    @PostMapping("/myPage/msg")
    public ResponseEntity<CommonResponse> sendMsg(@RequestBody MsgRequest msgRequest) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Msg sent successfully.", msgService.sendMsg(msgRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 삭제", notes = "메세지를 삭제한다.")
    @DeleteMapping("/myPage/msg")
    public ResponseEntity<CommonResponse> deleteMsg(@RequestBody List<Long> msgIds) {
        msgService.deleteMsg(msgIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Msg deleted successfully.", "deleted"), HttpStatus.OK);
    }
}
